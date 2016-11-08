package courseSelection.ontology;

import jade.content.Concept;

public class Student implements Concept{
	
	private String name;
	private float zScore;
	private int districtId;
	private int schemeId;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getzScore() {
		return zScore;
	}
	public void setzScore(float zScore) {
		this.zScore = zScore;
	}
	public int getDistrictId() {
		return districtId;
	}
	public void setDistrictId(int districtId) {
		this.districtId = districtId;
	}
	public int getSchemeId() {
		return schemeId;
	}
	public void setSchemeId(int schemeId) {
		this.schemeId = schemeId;
	}
}
