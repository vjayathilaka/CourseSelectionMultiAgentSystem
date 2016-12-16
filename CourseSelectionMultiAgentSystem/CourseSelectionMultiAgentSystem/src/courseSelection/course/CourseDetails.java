package courseSelection.course;

import java.util.Map;

import courseSelection.constants.SCHEME;
import courseSelection.ontology.District;
import courseSelection.ontology.University;

public class CourseDetails {
	private String courseName;
	private Map<Integer, University> offeredUniversitiesMap;
	private Map<Integer, District> districtZScoresMap;
	private Map<Integer, SCHEME> schemesMap;
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public Map<Integer, University> getOfferedUniversitiesMap() {
		return offeredUniversitiesMap;
	}
	public void setOfferedUniversitiesMap(Map<Integer, University> offeredUniversitiesMap) {
		this.offeredUniversitiesMap = offeredUniversitiesMap;
	}
	public Map<Integer, District> getDistrictZScoresMap() {
		return districtZScoresMap;
	}
	public void setDistrictZScoresMap(Map<Integer, District> districtZScoresMap) {
		this.districtZScoresMap = districtZScoresMap;
	}
	public Map<Integer, SCHEME> getSchemesMap() {
		return schemesMap;
	}
	public void setSchemesMap(Map<Integer, SCHEME> schemesMap) {
		this.schemesMap = schemesMap;
	}
}
