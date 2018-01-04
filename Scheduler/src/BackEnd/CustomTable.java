/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BackEnd;

import java.awt.Color;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.table.TableModel;

/**
 *
 * @author Hardkor
 */
public class CustomTable extends JTable{
    
    public CustomTable(CustomTableModel tableModel)
    {
        super(tableModel);
        for(int i = 0; i< this.getColumnCount(); i++)
        {
            CellRenderer cr = new CellRenderer();
            cr.setRowAndColor(0, Color.WHITE);
            this.getColumnModel().getColumn(i).setCellRenderer(cr);
        }
    }
    public void changeCellColor(int row, int col, Color color)
    {
        CellRenderer cr = new CellRenderer();
        cr.setRowAndColor(row, color);
        this.getColumnModel().getColumn(col).setCellRenderer(cr);
    }
    
}
