package courseSelection.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import courseSelection.constants.DISTRICT;
import courseSelection.constants.SCHEME;
import courseSelection.constants.UNIVERSITY;
import courseSelection.course.CourseAgent;
import courseSelection.ontology.Course;
import courseSelection.ontology.District;
import courseSelection.ontology.Student;
import courseSelection.ontology.University;

public class CourseGuiImp extends JFrame{
	
	private CourseAgent courseAgent;
	
	private JTextField courseName, lastYearCutOffGPA;

	/**
	 * Create the frame.
	 */
	public CourseGuiImp(CourseAgent cAgent) {
		this.courseAgent = cAgent;
		
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(2, 2));
		p.add(new JLabel("Course Name:"));
		courseName = new JTextField(15);
		p.add(courseName);
		p.add(new JLabel("GPA:"));
		lastYearCutOffGPA = new JTextField(15);
		p.add(lastYearCutOffGPA);
		getContentPane().add(p, BorderLayout.CENTER);
		
		JButton addButton = new JButton("Add");
		addButton.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				try {
					String cName = courseName.getText().trim();
					String cZScore = lastYearCutOffGPA.getText().trim();
					Course course = new Course();
					course.setCourseName(cName);
					course.setId(Integer.parseInt(cZScore));
					
					University u = new University();
					u.setId(UNIVERSITY.UniversityOfPeradeniya.getId());
					u.setUniversityName(UNIVERSITY.UniversityOfPeradeniya.getName());
					Map<Integer, University> uniMap = new HashMap<>();
					uniMap.put(UNIVERSITY.UniversityOfPeradeniya.getId(), u);
					
					District d = new District();
					d.setId(DISTRICT.Kegalle.getId());
					d.setDistrictName("kegalle");
					d.setzScore(1);
					Map<Integer, District> disMap = new HashMap<>();
					disMap.put(DISTRICT.Kegalle.getId(), d);
					
					Map<Integer, SCHEME> schemeMap = new HashMap<>();
					schemeMap.put(SCHEME.ART.getId(), SCHEME.ART);

					//courseAgent.updateCourseAgent(cName, uniMap, disMap, schemeMap);
					
					courseName.setText("");
					lastYearCutOffGPA.setText("");
				}
				catch (Exception e) {
					JOptionPane.showMessageDialog(CourseGuiImp.this, "Invalid values. "+e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); 
				}
			}
		} );
		p = new JPanel();
		p.add(addButton);
		getContentPane().add(p, BorderLayout.SOUTH);
		
		// Make the agent terminate when the user closes 
		// the GUI using the button on the upper right corner	
		addWindowListener(new	WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				//myAgent.doDelete();
			}
		} );
		
		setResizable(false);
	}
	
	public void showGui(){
		pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int centerX = (int)screenSize.getWidth() / 2;
		int centerY = (int)screenSize.getHeight() / 2;
		setLocation(centerX - getWidth() / 2, centerY - getHeight() / 2);
		super.setVisible(true);
	}

}
