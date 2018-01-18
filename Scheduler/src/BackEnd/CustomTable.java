/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BackEnd;

import java.awt.Color;
import java.awt.Point;
import java.util.List;
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
            CellRenderer cr = new CellRenderer(this.gridColor);
            //cr.setRowAndColor(0, Color.WHITE);
            this.getColumnModel().getColumn(i).setCellRenderer(cr);
        }
    }
    public void changeCellColor(int row, int col, Color color, Boolean first)
    {
        CellRenderer cr = (CellRenderer) this.getColumnModel().getColumn(col).getCellRenderer();
        cr.setRowAndColor(row, color,first);
    }
    public void changeCellColor(int startRow, int endRow, int col, Color color)
    {
        CellRenderer cr = (CellRenderer) this.getColumnModel().getColumn(col).getCellRenderer();
        if(startRow > endRow)
            return;
        for(int i = startRow; i <= endRow; i++)
        {
            cr.setRowAndColor(i, color, Boolean.FALSE);
        }
    }
    public void changeCellColor(List <Point> cells, Color color, Boolean first)
    {
        Boolean firstTmp = first;
        for(Point cell : cells)
        {
                this.changeCellColor(cell.x, cell.y, color,firstTmp);
                firstTmp = Boolean.FALSE;
        }
    }
    
}
