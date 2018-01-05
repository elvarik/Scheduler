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
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Hardkor
 */
public class CellRenderer extends DefaultTableCellRenderer{
    private int targetRow;
    private Color color;
    private Map<Integer, Color> rowMap;

    public CellRenderer() {
        this.rowMap = new TreeMap<>();
    }
    public void setRowAndColor(int row, Color color)
    {
        rowMap.put(row, color);
    }
    @Override
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

    JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
    
    if(isSelected)
    {
        l.setBackground(new Color(242, 242, 242));
    }
    else if(rowMap.get(row) != null)
      l.setBackground(rowMap.get(row));
    else
       l.setBackground(Color.WHITE);

  //Return the JLabel which renders the cell.
  return l;
  }
}
