package courseSelection.ontology;

import jade.content.Concept;

public class Student implements Concept{
	private int schemeId;
        
        private int subject1;
        private int subject2;
        private int subject3;
        private String oLEnglish;
        private String oLMaths;
        private float zScore;
	private int districtId;

    public int getSchemeId() {
        return schemeId;
    }

    public void setSchemeId(int schemeId) {
        this.schemeId = schemeId;
    }

    public int getSubject1() {
        return subject1;
    }

    public void setSubject1(int subject1) {
        this.subject1 = subject1;
    }

    public int getSubject2() {
        return subject2;
    }

    public void setSubject2(int subject2) {
        this.subject2 = subject2;
    }

    public int getSubject3() {
        return subject3;
    }

    public void setSubject3(int subject3) {
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

        
}
