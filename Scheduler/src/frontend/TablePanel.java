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
import BackEnd.Work;
import BackEnd.Worker;
import java.awt.BorderLayout;
import java.awt.Color;
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

/**
 *
 * @author Hardkor
 */
public class TablePanel extends JPanel implements ActionListener, ItemListener{
    
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
          
                data.addElement(Integer.toString(hour) + ":" + minuteString);
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
    public void changeCellColor(int startRow, int endRow, int col, Color color)
    {
        table.changeCellColor(startRow, endRow, col, color);
    }
    public void changeCellColor(int row, int col, Color color)
    {
        table.changeCellColor(row, col, color);
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
        Map <Date, Work> works = tmp.getWorksInMonth(currentDate.getMonth(), currentDate.getYear());
        if(!works.isEmpty())
        {
           for(Date key : works.keySet())
           {
               Work tmpWork = works.get(key);
               table.changeCellColor(tmpWork.getCells(), tmpWork.getColor());
           }
        }
        
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
}
