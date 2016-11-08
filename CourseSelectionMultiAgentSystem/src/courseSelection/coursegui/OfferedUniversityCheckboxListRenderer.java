/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package courseSelection.coursegui;

import java.awt.Component;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author NT
 */
public class OfferedUniversityCheckboxListRenderer extends JCheckBox implements ListCellRenderer<OfferedUniversity> {

    @Override
    public Component getListCellRendererComponent(JList<? extends OfferedUniversity> list, OfferedUniversity value, int index, boolean isSelected, boolean cellHasFocus) {
        setEnabled(list.isEnabled());
        setSelected(value.isSelected());
        setFont(list.getFont());
        setBackground(list.getBackground());
        setForeground(list.getForeground());
        setText(value.toString());
        return this;
    }

}
