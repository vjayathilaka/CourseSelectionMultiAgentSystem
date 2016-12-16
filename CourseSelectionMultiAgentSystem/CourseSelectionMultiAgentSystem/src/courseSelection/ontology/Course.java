package courseSelection.ontology;

import java.util.List;
import java.util.Map;

import courseSelection.constants.UNIVERSITY;
import jade.content.Concept;

public class Course implements Concept{
	
	private int id;
	private String courseName;
	private float zScore;
	private float zScoreDiff;
	//private Map<University, List<Integer>> universityStatus;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
/*	public Map<University, List<Integer>> getUniversityStatus() {
		return universityStatus;
	}
	public void setUniversityStatus(Map<University, List<Integer>> universityStatus) {
		this.universityStatus = universityStatus;
	}*/
	public float getzScore() {
		return zScore;
	}
	public void setzScore(float zScore) {
		this.zScore = zScore;
	}
	public float getzScoreDiff() {
		return zScoreDiff;
	}
	public void setzScoreDiff(float zScoreDiff) {
		this.zScoreDiff = zScoreDiff;
	}
	
	
}
