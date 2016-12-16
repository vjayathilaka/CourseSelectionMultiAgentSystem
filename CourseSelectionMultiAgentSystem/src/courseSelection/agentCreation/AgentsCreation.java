 package courseSelection.agentCreation;

import courseSelection.coursegui.OfferedUniversity;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AgentsCreation {
    
  // JDBC driver name and database URL
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://localhost/courseselection";

   //  Database credentials
   static final String USER = "root";
   static final String PASS = "hiran";
   
    public void createAgents() {
        jade.core.Runtime rt = jade.core.Runtime.instance();
        
        Profile p = new ProfileImpl();
        p.setParameter(Profile.MAIN_HOST, "192.168.1.4");
        p.setParameter(Profile.MAIN_PORT, "1099");
        p.setParameter(Profile.GUI, "true");
        
        ContainerController cc = rt.createMainContainer(p);
        if(cc != null){
            Connection conn = null;
            Statement stmt = null;
            try{
               Class.forName("com.mysql.jdbc.Driver");

               conn = DriverManager.getConnection(DB_URL, USER, PASS);

               stmt = conn.createStatement();

               String sql = "SELECT * FROM course";
               ResultSet rs = stmt.executeQuery(sql);
               AgentController ac = null;
               while(rs.next()){
                  int id  = rs.getInt("course_id");
                  int isEnglishCom = rs.getInt("english");
                  int isMathsCom = rs.getInt("maths");
                  int proposedIntake = rs.getInt("proposed_intake");
                  int schemaId = rs.getInt("schema_id");
                  String courseName = rs.getString("course_name");
                  ArrayList<ArrayList<SubjectCombination>> subjectsLists = getSubjectLists(conn, id);
                  List<Integer> offierdUniversities = getOfferedUniIds(conn, id);
                  Map<Integer, Float> districtZScoreMap = getDistrictZScoreMap(conn, id);
                  
                  Object[] argumentsToAgent = {courseName, subjectsLists, offierdUniversities, new Integer(isEnglishCom), new Integer(isMathsCom), new Integer(proposedIntake), new Integer(id),districtZScoreMap, new Integer(schemaId)};
                  
                  ac = cc.createNewAgent(courseName , "courseSelection.course.CourseAgent", argumentsToAgent);
                  ac.start();

               }
                  ac = cc.createNewAgent("studentAgent" , "courseSelection.student.StudentAgent", null);
                  ac.start();
               rs.close();
            }catch(SQLException se){
               //Handle errors for JDBC
               se.printStackTrace();
            }catch(Exception e){
               //Handle errors for Class.forName
               e.printStackTrace();
            }finally{
               //finally block used to close resources
               try{
                  if(stmt!=null)
                     conn.close();
               }catch(SQLException se){
               }// do nothing
               try{
                  if(conn!=null)
                     conn.close();
               }catch(SQLException se){
                  se.printStackTrace();
               }//end finally try
            }//end try
        }
}
   
   public ArrayList<ArrayList<SubjectCombination>> getSubjectLists(Connection conn, int courseId) throws SQLException{
      Statement stmt = conn.createStatement();

      String sql = "SELECT * FROM subject_com  where second_op_number !="+-1+" and course_id="+courseId;
      ResultSet rs = stmt.executeQuery(sql);
      ArrayList<ArrayList<SubjectCombination>> returnRs = new ArrayList<ArrayList<SubjectCombination>>();
      while(rs.next()){
         int subComId = rs.getInt("sub_com_id");
         int subListId = rs.getInt("subject_list_id");
         int subjectCount = rs.getInt("num_subj");
         int secondOpNumber = rs.getInt("second_op_number");
         List<Integer> subjectIds = getSubjectIds(conn, subListId);
         
         List<SubjectCombination> scList = new ArrayList<SubjectCombination>();
         SubjectCombination sc = new SubjectCombination();
         sc.setSubjectCount(subjectCount);
         sc.setSubjectIds(subjectIds);
         scList.add(sc);
         if(secondOpNumber != 0) {
            scList.add(getSubjectCombination(conn, secondOpNumber));
         }
         
         
         returnRs.add((ArrayList<SubjectCombination>) scList);
         
      }
      rs.close();
       return returnRs;
   }
   
   public List<Integer> getSubjectIds(Connection conn, int subComId) throws SQLException {
      Statement stmt = conn.createStatement();

      String sql = "SELECT * FROM sub_com_subjects  where sub_com_id="+subComId;
      ResultSet rs = stmt.executeQuery(sql);
      List<Integer> subjectIds = new ArrayList<>();
      while(rs.next()){
         int subId = rs.getInt("subject_id");
         subjectIds.add(subId);        
      }
      rs.close();
       return subjectIds;
   }
   
   public SubjectCombination getSubjectCombination(Connection conn, int id) throws SQLException {
       SubjectCombination sc = new SubjectCombination();
       
      Statement stmt = conn.createStatement();

      String sql = "SELECT * FROM subject_com  where second_op_number ="+-1+" and sub_com_id="+id;;
      ResultSet rs = stmt.executeQuery(sql);
      while(rs.next()){
         int subComId = rs.getInt("sub_com_id");
         int subCount = rs.getInt("num_subj");
         int sub_list_id = rs.getInt("subject_list_id");
         List<Integer> subjectIds = getSubjectIds(conn, sub_list_id); 
         sc.setSubjectCount(subCount);
         sc.setSubjectIds(subjectIds);
      }
      rs.close();
       
       return sc;
   }
   
   public List<Integer> getOfferedUniIds(Connection conn, int course_id) throws SQLException {
      Statement stmt = conn.createStatement();

      String sql = "SELECT * FROM offered_universities WHERE course_id="+course_id;
      ResultSet rs = stmt.executeQuery(sql);
      
      List<Integer> uniList = new ArrayList<>();
      while(rs.next()){
         int uniId = rs.getInt("uni_id");
         uniList.add(uniId);
      }
      rs.close();
       
       return uniList;
   }
   
   public Map<Integer, Float> getDistrictZScoreMap(Connection conn, int courseId) throws SQLException {
      Statement stmt = conn.createStatement();

      String sql = "SELECT * FROM district_zscore WHERE course_id="+courseId;
      ResultSet rs = stmt.executeQuery(sql);
      
      Map<Integer, Float> districtZscoreMap = new HashMap<>();
      while(rs.next()){
         int districtId = rs.getInt("district_id");
         float zScore = rs.getFloat("zscore");
         districtZscoreMap.put(districtId, zScore);
      }
      rs.close();
       
       return districtZscoreMap;
   }
        
}
    

