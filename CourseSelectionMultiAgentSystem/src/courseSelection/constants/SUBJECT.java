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
    
    MATHEMATICS(1, "Mathamatics"),
    PHYSICS(2, "Physics"),
    CHEMISTRY(3, "Chemistry"),
    OL_ENGLISH(4, "OL English"),
    OL_MATHS(5, "OL Maths");
    
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
