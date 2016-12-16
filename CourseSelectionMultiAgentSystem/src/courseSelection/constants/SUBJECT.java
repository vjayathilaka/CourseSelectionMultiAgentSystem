/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package courseSelection.constants;

/**
 *
 * @author Hiranthi
 */
public enum SUBJECT {
    CHEMISTRY(1, "Chemistry"),
    COMBINED_MATHS(2, "Combined Maths"),
    PHYSICS(3, "Physics"),
    BUSINESS_STUDIES(4, "Business Studies"),
    ECONOMICS(5, "Echonomics"),
    ACCOUNTING(6, "Accounting"),
    AGRICULTURAL_SCIENCE(7, "Agricultural Science"),
    GERMAN(8, "German"),
    ELEMENTS_OF_POLITICAL_SCIENCE(9, "Element Of Political Science"),
    LOGIC_AND_SCIENTIFIC_METHOD(10, "Logic & Scientific Methods"),
    INFORMATION_AND_COMMUNICATION_TECHNOLOGY(11, "Information & Communication Technology"),
    GEOGRAPHY(12, "Geography"),
    ENGLISH(14, "English"),
    FRENCH(15, "French"),
    BUSINESS_STATISTICS(16, "Business Sstatistics"),
    HISTORY(17, "History"),
    HOME_ECHONOMICS(18, "Home Echonomics"),
    COMMUNICATION_AND_MEDIA_STUDIES(19, "Communication and media studies"),
    CIVIL_TECHNOLOGY(20, "Civil Technology"),
    ELECTRONIC_ELECTRICAL_AND_INFORMATION_TECHNOLOGY(21, "Electronic, Electrical and infromation technology"),
    MECHANICAL_TECHNOLOGY(22, "Mechanical technologies"),
    FOOD_TECHNOLOGY(23, "Food Technology"),
    BIO_RESOURSE_TECHNOLOGY(24, "Bio Resourse Technology"),
    AGRO_TECHNOLOGY(25, "Agro Technology"),
    SINGHALA(26, "Singhala"),
    TAMIL(27, "Tamil"),
    BIOLOGY(28, "Biology");
    
    //OL_ENGLISH(4, "OL English"),
    //OL_MATHS(5, "OL Maths");
    
    int id;
    String name;
    
    private SUBJECT(int id, String name) {
        this.id = id;
        this.name = name; 
    }
    
    public int getId() {
        return this.id;
    }
    
    public String getName() {
        return this.name;
    }
    
}
