/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package distancecalculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hiranthi
 */
public class DistanceCalculator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        GoogleDistanceCalculator gc = new GoogleDistanceCalculator();
        Map<String, String> uniList = new HashMap<>();

        for(UNIVERSITY uni : UNIVERSITY.values()){
            uniList.put(uni.getName(), uni.getCordinates());
        }
        //uniList.add("8.366367,80.502356");
        try {
            Map<String, Float> uniMap = gc.getSourtedListAsDistance("colombo", uniList);
            
            for (Map.Entry<String, Float> entry : uniMap.entrySet()) {
                System.out.println("Key : " + entry.getKey()
                        + " Value : " + entry.getValue());
            }
            
        } catch (Exception ex) {
            Logger.getLogger(DistanceCalculator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
