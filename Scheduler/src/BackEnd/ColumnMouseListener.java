/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BackEnd;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Hardkor
 */
public class ColumnMouseListener implements MouseMotionListener, MouseListener{
    
    private CustomTable table;
    private Color background;
    private Color border;
    private int column;
    private HeaderCellRenderer unSelected, selected;
    public ColumnMouseListener(CustomTable table, Color background, Color border, int column)
    {
        this.table = table;
        this.background = background;
        this.border = border;
        this.column = column;
        this.unSelected = new HeaderCellRenderer(background);
        this.selected = new HeaderCellRenderer(background.brighter());
    }
    @Override
    public void mouseClicked(MouseEvent me) {
        
    }

    @Override
    public void mousePressed(MouseEvent me) {
        
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        int col = table.columnAtPoint(me.getPoint());
        if(col == column)
            table.getColumnModel().getColumn(column).setHeaderRenderer(selected);
        else
            table.getColumnModel().getColumn(column).setHeaderRenderer(unSelected);
    }

    @Override
    public void mouseExited(MouseEvent me) {
        table.getColumnModel().getColumn(column).setHeaderRenderer(unSelected);
    }

    @Override
    public void mouseDragged(MouseEvent me) {
        
    }

    @Override
    public void mouseMoved(MouseEvent me) {
        int col = table.getTableHeader().columnAtPoint(me.getPoint());
        if(col == column)
            table.getColumnModel().getColumn(column).setHeaderRenderer(selected);
        else
            table.getColumnModel().getColumn(column).setHeaderRenderer(unSelected);
    }
    
}
