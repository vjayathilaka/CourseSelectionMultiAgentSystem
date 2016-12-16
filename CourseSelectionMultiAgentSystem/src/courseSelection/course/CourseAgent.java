package courseSelection.course;

import courseSelection.agentCreation.SubjectCombination;
import java.util.List; 
import java.util.Map;

import courseSelection.ontology.Course;
import courseSelection.ontology.CourseSelectionOntology;
import courseSelection.ontology.Student;
import courseSelection.ontology.StudentCourseAction;
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
import jade.domain.DFService;
import jade.domain.FIPAException;
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
                List<Integer> studentSubList = new ArrayList<>();
                studentSubList.add(student.getSubject1());
                studentSubList.add(student.getSubject2());
                studentSubList.add(student.getSubject3());
                boolean flag = false;
                //check for subject combination
                boolean overalMach = false;
                FIRSTFOR:for(ArrayList<SubjectCombination> combinationList : courseList){
                    int count = 0;
                    System.out.println(courseName + " === course list count " + courseList.size());
                    System.out.println(courseName + " === subject list count " + combinationList.size());
                    for(SubjectCombination subjectCom : combinationList) {
                        System.out.println(courseName + " === subject count " + subjectCom.getSubjectCount());
                        
                        for(int id : subjectCom.getSubjectIds()){
                            System.out.println("Subject id = " + id);
                        }
                        
                        if(subjectCom.isContainAllSubjects(studentSubList)){
                            count++;
                        } 
                        if(count == combinationList.size()){
                            overalMach = true;
                            break FIRSTFOR;
                        }
                    }
                }
                //check schema is correct
                if((schemaId == 0 || student.getSchemeId() == schemaId) && overalMach){
                    //check district z-score is near
                    if(student.getzScore()== 0.0 || districtZScoreMap.get(student.getDistrictId()) == null || (districtZScoreMap.get(student.getDistrictId())-0.5) < student.getzScore()){
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
                                c.setId(courseId);
                                c.setUniversities(offeredUniversities);
                                c.setProposedIntake(proposedIntake);
                                if(student.getDistrictId() > 0 && districtZScoreMap.get(student.getDistrictId()) != null)
                                    c.setzScore(districtZScoreMap.get(student.getDistrictId()));
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
