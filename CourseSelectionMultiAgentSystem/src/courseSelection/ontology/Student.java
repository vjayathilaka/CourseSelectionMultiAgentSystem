package courseSelection.ontology;

import jade.content.Concept;

public class Student implements Concept{
	
	private String name;
	private int schemeId;
        
        private String subject1;
        private String subject2;
        private String subject3;
        private String oLEnglish;
        private String oLMaths;
        private float zScore;
	private int districtId;
  
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

    public String getSubject1() {
        return subject1;
    }

    public void setSubject1(String subject1) {
        this.subject1 = subject1;
    }

    public String getSubject2() {
        return subject2;
    }

    public void setSubject2(String subject2) {
        this.subject2 = subject2;
    }

    public String getSubject3() {
        return subject3;
    }

    public void setSubject3(String subject3) {
        this.subject3 = subject3;
    }

    public String getoLEnglish() {
        return oLEnglish;
    }

    public void setoLEnglish(String oLEnglish) {
        this.oLEnglish = oLEnglish;
    }

    public String getoLMaths() {
        return oLMaths;
    }

    public void setoLMaths(String oLMaths) {
        this.oLMaths = oLMaths;
    }
        
        
}
