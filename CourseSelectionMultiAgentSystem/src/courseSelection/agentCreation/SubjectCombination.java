package courseSelection.agentCreation;

import java.util.List;

public class SubjectCombination {
    private int subjectCount;
    private List<Integer> subjectIds;

    public int getSubjectCount() {
        return subjectCount;
    }

    public void setSubjectCount(int subjectCount) {
        this.subjectCount = subjectCount;
    }

    public List<Integer> getSubjectIds() {
        return subjectIds;
    }

    public void setSubjectIds(List<Integer> subjectIds) {
        this.subjectIds = subjectIds;
    }
    
    public boolean isContainAllSubjects(List<Integer> subList){
        int count = 0;
        for(int id : subList) {
            if(subjectIds.contains(id)){
                count++;
            }
        }
        return subjectCount <= count;
    }
}
