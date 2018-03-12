/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BackEnd;

import java.awt.Color;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Hardkor
 */
public class HeaderCellRenderer extends DefaultTableCellRenderer{
    
    private Color background;
    private Border border;
    public HeaderCellRenderer (Color background)
    {
        this.background = background;
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

    JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
    l.setForeground(Color.white);
    l.setBackground(background);

    //if()
    return l;

    }
    
}
