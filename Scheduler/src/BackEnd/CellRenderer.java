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
    public CellRenderer(Color gridColor) {
        this.rowMap = new TreeMap<>();
        this.gridColor = gridColor;
        this.firstMap = new TreeMap<>();
    }
    public void setRowAndColor(int row, Color color, Boolean first)
    {
        rowMap.put(row, color);
        this.firstMap.put(row, first);
    }
    @Override
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

    JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
    
    if(isSelected && col == 0)
        return l;
    else if(isSelected)
    {
        if(rowMap.get(row) != null)
            l.setBackground(rowMap.get(row).darker());
        else
        l.setBackground(new Color(242, 242, 242));
        //setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, gridColor));
    }
    else if(rowMap.get(row) != null)
    {
      l.setBackground(rowMap.get(row));
      if(firstMap.get(row) == Boolean.TRUE)
          setBorder(BorderFactory.createMatteBorder(1, 1, 0, 0, gridColor));
      else
      setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, gridColor));
    }
    else
    {  
        l.setBackground(Color.WHITE);
        setBorder(BorderFactory.createMatteBorder(1, 1, 0, 0, gridColor));
    }

  //Return the JLabel which renders the cell.
  return l;
  }
}
