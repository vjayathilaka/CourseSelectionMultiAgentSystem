/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package courseSelection.coursegui;

import java.io.Serializable;

/**
 *
 * @author NT
 */
public class OfferedUniversity implements Serializable {

    private String unversityName;
    private boolean isSelected;

    public OfferedUniversity(String unversityName) {
        this.unversityName = unversityName;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public boolean isSelected() {
        return isSelected;
    }
    
    public String getUniversityName() {
    	return unversityName;
    }

    @Override
    public String toString() {
        return unversityName;
    }
}
