package courseSelection.course;

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
	private Map<Integer, University> offeredUniversitiesMap;
	private Map<Integer, District> districtZScoresMap;
	private Map<Integer, SCHEME> schemesMap;
	
	private boolean argumentMoode = false;

	protected void setup() {

		// Register language and ontology
		getContentManager().registerLanguage(codec);
		getContentManager().registerOntology(ontology);

		catalogue = new Hashtable<>();
		offeredUniversitiesMap = new HashMap<>();
		districtZScoresMap = new HashMap<>();
		schemesMap = new HashMap<>();

		Object[] args = getArguments();
		// Create and show the GUI
		String dataval = "";
		if(args != null && args.length > 0) {
			for(int i=0; i<args.length; i++){
				dataval = dataval + args[i];
			}

			setArgumentDataToTheAgent(dataval);
			argumentMoode = true;
		} else {
			courseGui = new CourseSelectionDialog(this);
			courseGui.showGui();
		}


		// Register the book-selling service in the yellow pages
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

		// Add the behaviour serving queries from buyer agents
		addBehaviour(new OfferRequestsServer());

		// Add the behaviour serving purchase orders from buyer agents
		// addBehaviour(new PurchaseOrdersServer());
	}
	
	private void setArgumentDataToTheAgent(String jsonArgument) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			jsonArgument = jsonArgument.replaceAll("5a5", ",");
			CourseDetails courseDetails = mapper.readValue(jsonArgument, CourseDetails.class);

			courseName = courseDetails.getCourseName();
			offeredUniversitiesMap = courseDetails.getOfferedUniversitiesMap();
			districtZScoresMap = courseDetails.getDistrictZScoresMap();
			schemesMap = courseDetails.getSchemesMap();
			
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Put agent clean-up operations here
	protected void takeDown() {
		// Deregister from the yellow pages
		try {
			DFService.deregister(this);
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}
		// Close the GUI
		if(argumentMoode)
			courseGui.dispose();
		// Printout a dismissal message
		System.out.println("Course agent " + getAID().getName() + " terminating.");
	}

	/**
	 * This is invoked by the GUI when the user adds a new book for sale
	 */
	public void updateCourseAgent(final String cName, final Map<Integer, University> universitiesMap,
			final Map<Integer, District> districtMap, final Map<Integer, SCHEME> sMap) {
		addBehaviour(new OneShotBehaviour() {
			public void action() {
				System.out.println("cname is " + cName);
				courseName = cName;
				offeredUniversitiesMap = universitiesMap;
				districtZScoresMap = districtMap;
				schemesMap = sMap;

				System.out.println("Agent updated with values-->");

			}
		});
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
		
		ResultProcessor processor = new ResultProcessor(offeredUniversitiesMap, districtZScoresMap, schemesMap);
                
                float districtZscore = districtZScoresMap.get(student.getDistrictId()).getzScore();
                float diff = student.getzScore() - districtZscore;
                
		if(diff >=0 && schemesMap.containsKey(student.getSchemeId())) {
			Course c = studentCourseAction.getCourse();
			c.setCourseName(courseName);
			c.setId(1);
			c.setzScoreDiff(processor.getZScoreDiffWithPastZScore(student.getDistrictId(), student.getzScore()));
			c.setzScore(processor.getPreviousZScore(student.getDistrictId()));
			
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
