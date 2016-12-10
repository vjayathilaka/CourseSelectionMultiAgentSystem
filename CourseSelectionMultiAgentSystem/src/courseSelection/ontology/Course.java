package courseSelection.ontology;

import jade.content.Concept;
import java.util.List;

public class Course implements Concept{
	
	private int id;
	private String courseName;
	private float zScore;
	private float zScoreDiff;
	private List universities;
	
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

    public List getUniversities() {
        return universities;
    }

    public void setUniversities(List universities) {
        this.universities = universities;
    }
	
	
}
