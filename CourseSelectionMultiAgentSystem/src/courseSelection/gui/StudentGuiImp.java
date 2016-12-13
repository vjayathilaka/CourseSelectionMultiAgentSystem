package courseSelection.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import courseSelection.constants.DISTRICT;
import courseSelection.constants.SCHEME;
import courseSelection.ontology.Course;
import courseSelection.ontology.Student;
import courseSelection.ontology.StudentCourseAction;
import courseSelection.student.StudentAgent;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

public class StudentGuiImp extends JFrame{

	/**
	 * Launch the application.
	 */
/*	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StudentGuiImp frame = new StudentGuiImp();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/
	
	private StudentAgent studentAgent;
	
	private JTextField name, gpa;

	/**
	 * Create the frame.
	 */
	public StudentGuiImp(StudentAgent sAgent) {
		this.studentAgent = sAgent;
		
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(2, 2));
		p.add(new JLabel("Name:"));
		name = new JTextField(15);
		p.add(name);
		p.add(new JLabel("GPA:"));
		gpa = new JTextField(15);
		p.add(gpa);
		getContentPane().add(p, BorderLayout.CENTER);
		
		JButton addButton = new JButton("Add");
		addButton.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				try {
					String studentName = name.getText().trim();
					String studentGpa = gpa.getText().trim();
					
					Student student = new Student();
					//student.setName(studentName);
					student.setzScore(Integer.parseInt(studentGpa));
					student.setDistrictId(DISTRICT.Kegalle.getId());
					student.setSchemeId(SCHEME.ART.getId());
					
					StudentCourseAction studentCourseAction = new StudentCourseAction();
					studentCourseAction.setStudent(student);
					Course course = new Course();
					course.setId(1);
					course.setzScore(1);
					course.setCourseName("art");
					studentCourseAction.setCourse(course);

					//myAgent.updateCatalogue(title, Integer.parseInt(price));
					//studentAgent.sendInformationToCourseAgent(studentCourseAction);
					name.setText("");
					gpa.setText("");
				}
				catch (Exception e) {
					JOptionPane.showMessageDialog(StudentGuiImp.this, "Invalid values. "+e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); 
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
	
	public void hide() {
		//super.setVisible(false);
	}

}
