/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BackEnd;

import java.util.Vector;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Hardkor
 */
public class CustomTableModel extends AbstractTableModel {
    private Vector <String> columnNames;
    private Vector <Vector> dataRows;
    public CustomTableModel( Vector rowData, Vector columnNames)
    {
        this.dataRows = rowData;
        this.columnNames = columnNames;
    }
    @Override
    public int getRowCount() {
        return dataRows.size();
    }
    @Override
    public String getColumnName(int col) {
            return columnNames.get(col).toString();
    }
    @Override
    public int getColumnCount() {
        return columnNames.size();
    }

    @Override
    public Object getValueAt(int i, int i1) {
        return dataRows.get(i).get(i1);
    }
    @Override
    public boolean isCellEditable(int row, int col)
        { return false; }
    @Override
    public void setValueAt(Object value, int row, int col) {
        dataRows.get(row).set(col, value);
        fireTableCellUpdated(row, col);
    }

    
    
}
