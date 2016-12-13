package courseSelection.course;

import courseSelection.agentCreation.SubjectCombination;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List; 
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;


import courseSelection.constants.SCHEME;
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

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Hashtable<Integer, String> catalogue;
	private CourseSelectionDialog courseGui;

	private Codec codec = new SLCodec();
	private Ontology ontology = CourseSelectionOntology.getInstance();

	private String courseName;
        private ArrayList<ArrayList<SubjectCombination>> courseList;
	private List<Integer> offeredUniversities;
        private int isEnglishComp;
        private int isMathsComp;
        
	private Map<Integer, District> districtZScoresMap;
	private Map<Integer, SCHEME> schemesMap;

	protected void setup() {

		// Register language and ontology
		getContentManager().registerLanguage(codec);
		getContentManager().registerOntology(ontology);

                Object[] argumentsOfAgent = getArguments();
                
                courseName = (String) argumentsOfAgent[0];
                courseList = (ArrayList<ArrayList<SubjectCombination>>) argumentsOfAgent[1];
                offeredUniversities = (List<Integer>) argumentsOfAgent[2];
                isEnglishComp = (int) argumentsOfAgent[3];
                isMathsComp = (int) argumentsOfAgent[4];

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
					//StudentCourseAction scAction = (StudentCourseAction) act.getAction();
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
                
                System.out.println("processed student data");
                System.out.println(student.getSubject1());
                System.out.println(student.getSubject2());
                System.out.println(student.getSubject3());
		
		//ResultProcessor processor = new ResultProcessor(offeredUniversities, districtZScoresMap, schemesMap);
                
                float districtZscore = districtZScoresMap.get(student.getDistrictId()).getzScore();
                float diff = student.getzScore() - districtZscore;
                
		if(true){//diff >=0 && schemesMap.containsKey(student.getSchemeId())) {
			Course c = studentCourseAction.getCourse();
			c.setCourseName(courseName);
			c.setId(1);
			//c.setzScoreDiff(processor.getZScoreDiffWithPastZScore(student.getDistrictId(), student.getzScore()));
			//c.setzScore(processor.getPreviousZScore(student.getDistrictId()));
                        List universities = new ArrayList();
                        University u1 = new University();
                        u1.setId(1);
                        u1.setUniversityName("Peradeniya");
                        universities.add(u1);
                        University u2 = new University();
                        u2.setId(2);
                        u2.setUniversityName("Colombo");
                        universities.add(u2);
                        
                        c.setUniversities(universities);
                        
			
			sendReplyMessage(ACLMessage.INFORM, message, action, agent);
		} /*else if(processor.machWithSchem(student.getSchemeId())){
			Course c = studentCourseAction.getCourse();
			c.setCourseName(courseName);
			c.setId(1);
			c.setzScoreDiff(processor.getZScoreDiffWithPastZScore(student.getDistrictId(), student.getzScore()));
			c.setzScore(processor.getPreviousZScore(student.getDistrictId()));
			
			sendReplyMessage(ACLMessage.REFUSE, message, action, agent);
		} */else {
			sendReplyMessage(ACLMessage.CANCEL, message, action, agent);
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
