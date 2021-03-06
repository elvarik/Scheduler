/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BackEnd;

import java.awt.Color;
import java.awt.Component;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Hardkor
 */
public class CellRenderer extends DefaultTableCellRenderer{
    private Map<Integer, Color> rowMap;
    private Map<Integer, Boolean> firstMap;
    private Color gridColor;
    private int coulumnCount, rowCount;
    public CellRenderer(Color gridColor, int columnCount, int rowCount) {
        this.rowMap = new TreeMap<>();
        this.gridColor = gridColor;
        this.firstMap = new TreeMap<>();
        this.coulumnCount = columnCount;
        this.rowCount = rowCount;
    }
    public void setRowAndColor(int row, Color color, Boolean first)
    {
        rowMap.put(row, color);
        this.firstMap.put(row, first);
    }
    @Override
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
    
//    if(rowMap.get(row) != null)
//        hasFocus=false;
    JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
    
    if(isSelected)
    {
        if(rowMap.get(row) != null)
        {
            //l.setForeground(Color.WHITE);
            l.setBackground(rowMap.get(row));
            //l.setForeground(Color.black);
        }
//        else
//        l.setBackground(new Color(39, 60, 117).brighter());
        
    }
    else if(rowMap.get(row) != null)
    {
      l.setForeground(Color.WHITE);
      l.setBackground(rowMap.get(row));
      if(firstMap.get(row) == Boolean.TRUE)
          setBorder(BorderFactory.createMatteBorder(1, 1, 0, 0, gridColor));
      else
      setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, gridColor));
    }
    else
    {  
        l.setBackground(Color.WHITE);
        if(col == this.coulumnCount -1 && row == this.rowCount-1)
            setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, gridColor));
        else if(col == this.coulumnCount-1)
            setBorder(BorderFactory.createMatteBorder(1, 1, 0, 1, gridColor));
        else if(row == this.rowCount -1)
            setBorder(BorderFactory.createMatteBorder(1, 1, 1, 0, gridColor));
        else
            setBorder(BorderFactory.createMatteBorder(1, 1, 0, 0, gridColor));
            
    }

  //Return the JLabel which renders the cell.
  return l;
  }
}
