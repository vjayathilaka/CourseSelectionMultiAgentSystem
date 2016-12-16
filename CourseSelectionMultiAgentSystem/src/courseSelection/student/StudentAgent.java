package courseSelection.student;

import java.util.ArrayList;
import java.util.List;

import courseSelection.constants.DISTRICT;
import courseSelection.constants.SUBJECT;
import courseSelection.gui.StudentAgentGUI;
import courseSelection.gui.StudentGui;
import courseSelection.gui.StudentGuiImp;
import courseSelection.ontology.Course;
import courseSelection.ontology.CourseSelectionOntology;
import courseSelection.ontology.Student;
import courseSelection.ontology.StudentCourseAction;
import courseSelection.ontology.University;
import jade.content.AgentAction;
import jade.content.Concept;
import jade.content.ContentElement;
import jade.content.ContentElementList;
import jade.content.lang.Codec;
import jade.content.lang.Codec.CodecException;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import sun.security.x509.X509CertInfo;

public class StudentAgent extends Agent {

	private static final long serialVersionUID = 1L;
	private StudentGui studentGui;
	private AID[] courseAgents;
	private StudentCourseAction studentCourseAction;

	private Codec codec = new SLCodec();
	private Ontology ontology = CourseSelectionOntology.getInstance();

	public void setup() {
                System.out.println("set up");
		// Register language and ontology
		getContentManager().registerLanguage(codec);
		getContentManager().registerOntology(ontology);

		studentGui = new StudentGui(this);
		studentGui.showGui();
	}

	protected void takeDown() {
		try {
			DFService.deregister(this);
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}
		// Close the GUI
		studentGui.dispose();
		// Printout a dismissal message
		System.out.println("Student agent " + getAID().getName() + " terminating.");

	}

	public void sendInformationToCourseAgent(int sub1Id, int sub2Id, int sub3Id, int schemaId, int districtId, float zScore, String olEnglish, String olMaths) {                
            
                StudentCourseAction sca = new StudentCourseAction();
                Student s = new Student();
                s.setSubject1(sub1Id);
                s.setSubject2(sub2Id);
                s.setSubject3(sub3Id);
                s.setDistrictId(districtId);
                s.setSchemeId(schemaId);
                s.setzScore(zScore);
                s.setoLEnglish(olEnglish);
                s.setoLMaths(olMaths);
                sca.setStudent(s);
                
                Course c = new Course();
                c.setId(1);
                List list1 = new ArrayList();
                list1.add(1);
                c.setUniversities(list1);
                sca.setCourse(c);
		this.studentCourseAction = sca;
		addBehaviour(new CourseInfomationHandler());
	}
	
	private class CourseInfomationHandler extends OneShotBehaviour {

		@Override
		public void action() {
			System.out.println("course infor handler on tick");

			DFAgentDescription template = new DFAgentDescription();
			ServiceDescription sd = new ServiceDescription();
			sd.setType("course-selection");
			template.addServices(sd);
			try {
				DFAgentDescription[] result = DFService.search(myAgent, template);
				System.out.println("Found the following seller agents:");
				courseAgents = new AID[result.length];
				for (int i = 0; i < result.length; ++i) {
					courseAgents[i] = result[i].getName();
					System.out.println(courseAgents[i].getName());
				}
			} catch (FIPAException fe) {
				fe.printStackTrace();
			}

			// Perform the request
			myAgent.addBehaviour(new RequestPerformer());
		}
		
	}

	private class RequestPerformer extends Behaviour {

		private int repliesCnt = 0; // The counter of replies from seller agents
		private MessageTemplate mt; // The template to receive replies
		private int step = 0;
		private List<Course> courses = new ArrayList<>();
		private List<Course> rejectCourses = new ArrayList<>();

		public void action() {
			switch (step) {
			case 0:
                                System.out.println("Student request preformaer action");
				for (int i = 0; i < courseAgents.length; i++) {
                                        System.out.println("send messeage to agent");
                                        System.out.println(studentCourseAction.getStudent().getSubject1());
					sendMessage(ACLMessage.REQUEST, studentCourseAction, courseAgents[i]);
				}
				step = 1;
				break;
			case 1:
				ACLMessage reply = myAgent.receive();
				if (reply != null) {
					try {
						ContentElement content = getContentManager().extractContent(reply);
						StudentCourseAction action = (StudentCourseAction) ((Action) content).getAction();

						switch (reply.getPerformative()) {
							case (ACLMessage.INFORM):
					        	courses.add((Course)action.getCourse());
								break;
							case (ACLMessage.REFUSE):
								rejectCourses.add(action.getCourse());
								break;
							case (ACLMessage.CANCEL):
								break;
							default:
								break;
						}
					} catch (CodecException | OntologyException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					repliesCnt++;

					if (repliesCnt >= courseAgents.length) {
						// We received all replies
						if(courses.size() > 0){
							studentGui.updateStudentGUI(courses);
						} else {
							studentGui.updateStudentGUI(new ArrayList<Course>());
						}
						step = 2;
					}
				} else {
					block();
				}
				break;
			}
		}

		public boolean done() {
			System.out.println("step is :" + step);
			return (step == 2);
		}
	}

	private synchronized void sendMessage(int performative, AgentAction action, AID aid) {
		ACLMessage msg = new ACLMessage(performative);
		msg.setLanguage(codec.getName());
		msg.setOntology(ontology.getName());
		try {
			getContentManager().fillContent(msg, new Action(aid, action));
			msg.addReceiver(aid);
			send(msg);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
