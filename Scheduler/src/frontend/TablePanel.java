/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend;

import BackEnd.CalendarPlotter;
import BackEnd.CustomTable;
import BackEnd.CustomTableModel;
import BackEnd.Date;
import BackEnd.DateTime;
import BackEnd.Work;
import BackEnd.Worker;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;

/**
 *
 * @author Hardkor
 */
public class TablePanel extends JPanel implements ActionListener, ItemListener, TableColumnModelListener{
    
    private List <Worker> workersList;
    private JButton today;
    private JButton left;
    private JButton right;
    private JPanel header;
    private JPanel headerCenter;
    private CalendarPlotter cal;
    private JLabel dateLabel;
    private Date currentDate;
    private CustomTable table;
    private JComboBox workersSelectionBox;
    private int selectedItemIndex = 0;
    public TablePanel(List <Worker> workersList, Date date)
    {
        super(new BorderLayout());
        today = new JButton("Dzisiaj");
        left = new JButton("<");
        right = new JButton(">");
        left.addActionListener(this);
        right.addActionListener(this);
        today.addActionListener(this);
        header = new JPanel(new BorderLayout());
        headerCenter = new JPanel();
        cal = new CalendarPlotter();
        
        currentDate = date;
        this.workersList = workersList;
        dateLabel = new JLabel(date.monthYearString());
        header.add(today, BorderLayout.LINE_START);
        headerCenter.add(dateLabel);
        headerCenter.add(left);
        headerCenter.add(right);
        header.add(headerCenter, BorderLayout.CENTER);
        setTable(currentDate, workersList);
        
    }

    public void setTable(Date date, List <Worker> workersList)
    {
        currentDate = date;
        this.workersList = workersList;
        if(workersList.size() == 0)
            workersSelectionBox = new JComboBox();
        else
        {
            workersSelectionBox = new JComboBox(workersList.toArray());
            workersSelectionBox.setSelectedIndex(selectedItemIndex);
        }
        workersSelectionBox.addItemListener(this);
        dateLabel.setText(currentDate.monthYearString());
       // header.remove(0);
        //header.add(dateLabel, 0);
        header.add(workersSelectionBox, BorderLayout.LINE_END);
        header.setBorder(new EmptyBorder(7,7,7,7));
        this.add(header,BorderLayout.PAGE_START);
        
        Vector <String> columnNames = new Vector<String>();
        Vector<Vector> rowData = new Vector <Vector>();
        
        columnNames.addElement("");
        String firstDay = cal.getDayName(new Date(1, currentDate.getMonth(), currentDate.getYear()));
        int dayNumber = 0;
        for(int i = 0; i < cal.Days.length; i++)
        {
            if(firstDay.equals(cal.Days[i]))
                break;
            dayNumber++;
        }
        for(int i = 0; i < cal.getAmountOfDays(date.getMonth()); i++)
        {
            columnNames.addElement( Integer.toString(i+1) + " " + cal.Days[dayNumber %7]);
            dayNumber++;
        }
        int minute;
        String minuteString;
        for(int hour = 9; hour< 19; hour++)
        {
            minute = 0;
            for(int i = 0; i < 4; i++)
            {
                Vector<String> data = new Vector<String>();
                if(minute == 0)
                    minuteString = "00";
                else
                    minuteString = Integer.toString(minute);
          
                data.addElement(" " + Integer.toString(hour) + ":" + minuteString);
                for(int j = 0; j < cal.getAmountOfDays(currentDate.getMonth()); j++)
                    data.addElement("");
                minute+=15;
                rowData.addElement(data);
            } 
        }
        CustomTableModel cTable = new CustomTableModel(rowData, columnNames);

        table = new CustomTable(cTable);
        JScrollPane scrollPane = new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setCellSelectionEnabled(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.setRowHeight(20);
        table.setShowGrid(false);
        table.getColumnModel().addColumnModelListener(this);
        table.setIntercellSpacing(new Dimension(0,0));
        
        this.setWorkCells();
        this.add(scrollPane, BorderLayout.CENTER);
    }
    @Override
    public void actionPerformed(ActionEvent ae) {
        Object source = ae.getSource();
        if(source == right)
        {
            
            selectedItemIndex = workersSelectionBox.getSelectedIndex();
            header.remove(header.getComponentCount() -1);
            this.removeAll();
            if(currentDate.getMonth() == 12)
                this.setTable(new Date(1,currentDate.getYear()+1), workersList);
            else
                this.setTable(new Date(currentDate.getMonth()+1,currentDate.getYear()), workersList);
            this.revalidate();
            this.repaint();
        }
        else if(source == left)
        {
            selectedItemIndex = workersSelectionBox.getSelectedIndex();
            header.remove(header.getComponentCount() -1);
            this.removeAll();
            if(currentDate.getMonth() == 1)
                this.setTable(new Date(12,currentDate.getYear()-1), workersList);
            else
                this.setTable(new Date(currentDate.getMonth()-1,currentDate.getYear()), workersList);
            this.revalidate();
            this.repaint();
        }
        else if(source == today)
        {
            selectedItemIndex = workersSelectionBox.getSelectedIndex();
            header.remove(header.getComponentCount() -1);
            
            this.removeAll();
            this.setTable(new Date(), workersList);
            this.revalidate();
            this.repaint();
        }
    }
    public void changeCellColor(int row, int col, Color color, Boolean first)
    {
        table.changeCellColor(row, col, color,first);
    }
    public void setWorkers(List <Worker> workers)
    {
        this.workersList = workers;
        header.remove(header.getComponentCount() -1);
        if(workersList.isEmpty())
            workersSelectionBox = new JComboBox();
        else
        {
            workersSelectionBox = new JComboBox(workersList.toArray());
            workersSelectionBox.setSelectedIndex(0);
        }
        workersSelectionBox.addItemListener(this);
        header.add(workersSelectionBox, BorderLayout.LINE_END);
        header.revalidate();
        header.repaint();
    }
    private void setWorkCells()
    {
        
        selectedItemIndex = workersSelectionBox.getSelectedIndex();
        Worker tmp = workersList.get(selectedItemIndex);
        Map <DateTime, Work> works = tmp.getWorksInMonth(currentDate.getMonth(), currentDate.getYear());
        if(!works.isEmpty())
        {
           for(DateTime key : works.keySet())
           {
               
               Work tmpWork = works.get(key);
               List <Point> workCells = tmpWork.getCells();
               for(Point tmpCell : workCells)
               {
                   table.setValueAt("", tmpCell.x, tmpCell.y);
               }
               Point tmpPoint = (Point)tmpWork.getCells().get(0);
               table.changeCellColor(tmpWork.getCells(), tmpWork.getColor(), Boolean.TRUE);
               List<String> tmpSubstrings = substrings(tmpWork.getWorkDescription(), tmpPoint.y, tmpWork.getCells().size());
               int row = tmpPoint.x;
               for(String substring : tmpSubstrings)
               {
                   table.setValueAt(substring, row, tmpPoint.y);
                   row++;
               }
           }
        }
        
    }
    private List<String> substrings(String source, int column, int cellsAmount)
    {
        List<String> strings = new ArrayList<>();
        int width = table.getColumnModel().getColumn(column).getWidth()/8;
        if(width > source.length())
        {
            strings.add(" " + source);
            return strings;
        }
        String line = " ";
        String [] words = source.split(" ");
        
        for(String word : words)
        {
            if(strings.size() < cellsAmount -1)
            {
                if(line.length() < width)
                line+=word + " ";
                else
                {
                    strings.add(line);
                    line=" " +word + " ";
                }
            }
            else
            {
                line+=word +" ";
            }
        }
        strings.add(line);
        return strings;
    }

    @Override
    public void itemStateChanged(ItemEvent ie) {
        selectedItemIndex = workersSelectionBox.getSelectedIndex();
        header.remove(header.getComponentCount() -1);
        this.removeAll();
        this.setTable(currentDate, workersList);
        this.setWorkCells();
        this.revalidate();
        this.repaint();
    }

    @Override
    public void columnAdded(TableColumnModelEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void columnRemoved(TableColumnModelEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void columnMoved(TableColumnModelEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void columnMarginChanged(ChangeEvent e) {
        this.setWorkCells();
    }

    @Override
    public void columnSelectionChanged(ListSelectionEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
