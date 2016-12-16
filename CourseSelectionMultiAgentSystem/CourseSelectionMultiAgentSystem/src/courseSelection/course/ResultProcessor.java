package courseSelection.course;

import java.util.Map;

import courseSelection.constants.SCHEME;
import courseSelection.ontology.District;
import courseSelection.ontology.University;

public class ResultProcessor {
	
	private Map<Integer, University> universities;
	private Map<Integer, District> districts;
	private Map<Integer, SCHEME> schemes;
	
	public ResultProcessor(Map<Integer, University> universities, Map<Integer, District> districts,
			Map<Integer, SCHEME> schemes) {
		this.universities = universities;
		this.districts = districts;
		this.schemes = schemes;
		
	}
	
	public boolean machWithSchem(int schemeId) {
		
		if(schemes.containsKey(schemeId)) {
			return true;
		} else {
			return false;
		}
	}
	
	public float getPreviousZScore(int districtId) {
		District district = districts.get(districtId);
		return district.getzScore();
	}
	
	public float getZScoreDiffWithPastZScore(int districtId, float studentZScore) {
		District district = districts.get(districtId);
		
		return (studentZScore - district.getzScore());
	}
	
	
	
	public boolean isEligible(int schemeId, int districtId, float studentZScore) {
		if(!machWithSchem(schemeId)) {
			return false;
		}
                if(getZScoreDiffWithPastZScore(districtId, studentZScore) < 0.2) {
			return true;
		}
		return false;
	}
}
