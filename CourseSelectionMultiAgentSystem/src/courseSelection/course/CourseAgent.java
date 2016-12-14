package courseSelection.course;

import courseSelection.agentCreation.SubjectCombination;
import java.util.List; 
import java.util.Map;

import courseSelection.coursegui.CourseSelectionDialog;
import courseSelection.gui.CourseGuiImp;
import courseSelection.ontology.Course;
import courseSelection.ontology.CourseSelectionOntology;
import courseSelection.ontology.District;
import courseSelection.ontology.Student;
import courseSelection.ontology.StudentCourseAction;
import courseSelection.ontology.University;
import jade.content.ContentElementList;
import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.lang.Codec.CodecException;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import java.util.ArrayList;

public class CourseAgent extends Agent {

	private static final long serialVersionUID = 1L;

	private Codec codec = new SLCodec();
	private Ontology ontology = CourseSelectionOntology.getInstance();

        private int courseId;
	private String courseName;
        private ArrayList<ArrayList<SubjectCombination>> courseList;
	private List offeredUniversities;
        private int isEnglishComp;
        private int isMathsComp;
        private int proposedIntake;
        private int schemaId;
        private Map<Integer, Float> districtZScoreMap;

	protected void setup() {

		// Register language and ontology
		getContentManager().registerLanguage(codec);
		getContentManager().registerOntology(ontology);

                Object[] argumentsOfAgent = getArguments();
                
                courseName = (String) argumentsOfAgent[0];
                courseList = (ArrayList<ArrayList<SubjectCombination>>) argumentsOfAgent[1];
                offeredUniversities = (List) argumentsOfAgent[2];
                isEnglishComp = (int) argumentsOfAgent[3];
                isMathsComp = (int) argumentsOfAgent[4];
                proposedIntake = (int) argumentsOfAgent[5];
                courseId = (int) argumentsOfAgent[6];
                districtZScoreMap = (Map<Integer, Float>) argumentsOfAgent[7];
                schemaId = (int) argumentsOfAgent[8];

		// Register the Course agent service in the yellow pages
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("course-selection");
		sd.setName("JADE-course-selection");
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}

		// Add the behaviour serving queries from student agents
		addBehaviour(new OfferRequestsServer());
        }

	// Put agent clean-up operations here
	protected void takeDown() {
		// Deregister from the yellow pages
		try {
			DFService.deregister(this);
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}
		System.out.println("Course agent " + getAID().getName() + " terminating.");
	}

	private class OfferRequestsServer extends CyclicBehaviour {
		public void action() {
			MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
			ACLMessage msg = myAgent.receive(mt);
			if (msg != null) {
				ContentManager cm = myAgent.getContentManager();
				try {
					Action act = (Action) cm.extractContent(msg);
					StudentCourseAction scAction = (StudentCourseAction) act.getAction();
					processStudentData(msg, act, myAgent);

				} catch (CodecException | OntologyException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				block();
			}
		}
	}
	
	private synchronized void processStudentData(ACLMessage message, Action action, Agent agent) {
		StudentCourseAction studentCourseAction = (StudentCourseAction) action.getAction();
		Student student = studentCourseAction.getStudent();
                boolean flag = false;
                //check schema is correct
                if(schemaId == 0 || student.getSchemeId() == schemaId){
                    //check district z-score is near 
                    if((districtZScoreMap.get(student.getDistrictId())-0.5) < student.getzScore()){
                        //check requred O/L english results
                        if(isEnglishComp == 0 || student.getoLEnglish()=="A" || student.getoLEnglish()=="B" || student.getoLEnglish()=="C"){
                            if(isMathsComp == 0 || student.getoLMaths()=="A" || student.getoLMaths()=="B" || student.getoLMaths()=="C"){
                                flag = true;
                            }
                        }
                        
                    }
                    
                }
                

		if(flag){
                                Course c = studentCourseAction.getCourse();
                                c.setCourseName(courseName);
                                c.setId(1);
                                c.setUniversities(offeredUniversities);
                                c.setProposedIntake(proposedIntake);
                                if(isMathsComp == 1){
                                    c.setOlMaths("C");
                                } else {
                                    c.setOlMaths("N/A");
                                }    
                                if(isEnglishComp == 1){
                                    c.setOlEnglish("C");
                                } else {
                                    c.setOlEnglish("N/A");
                                }
                                sendReplyMessage(ACLMessage.INFORM, message, action, agent);
		} else {
			sendReplyMessage(ACLMessage.REFUSE, message, action, agent);
		}
	}

	private synchronized void sendReplyMessage(int performatice, ACLMessage message, Action action, Agent agent) {
		ACLMessage reply = message.createReply();
		reply.setPerformative(performatice);


		ContentElementList cel = new ContentElementList();
		cel.add(action);

		try {
			agent.getContentManager().fillContent(reply, cel);
		} catch (CodecException | OntologyException e) {
			e.printStackTrace();
		}

		agent.send(reply);
	}
}
