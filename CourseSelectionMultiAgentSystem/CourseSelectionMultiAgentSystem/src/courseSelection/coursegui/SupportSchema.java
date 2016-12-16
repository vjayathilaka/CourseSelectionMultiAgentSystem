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
public class SupportSchema implements Serializable{
    private String schema;
    private boolean isSelected;

    public SupportSchema(String schema) {
        this.schema = schema;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public boolean isSelected() {
        return isSelected;
    }
    
    public String getSchemaName(){
    	return schema;
    }
    
    @Override
    public String toString() {
        return schema;
    }
}
