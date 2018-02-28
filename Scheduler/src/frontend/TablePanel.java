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
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.TableColumn;
import BackEnd.TableColumnAdjuster;
/**
 *
 * @author Hardkor
 */
public class TablePanel extends JPanel implements ActionListener, ItemListener, TableColumnModelListener, ListSelectionListener{
    
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
    private CustomTable timeTable;
    private JComboBox workersSelectionBox;
    private int selectedItemIndex = 0;
    private boolean dayMode = false;
    private Date selectedDay = new Date();
    
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
        header.setBackground(new Color(39, 60, 117).brighter());
        headerCenter.setBackground(header.getBackground());
        left.setBackground(header.getBackground());
        right.setBackground(header.getBackground());
        today.setBackground(header.getBackground());
        cal = new CalendarPlotter();
        
        currentDate = date;
        this.workersList = workersList;
        dateLabel = new JLabel(date.monthYearString());
        dateLabel.setForeground(Color.WHITE);
        
        header.add(today, BorderLayout.LINE_START);
        headerCenter.add(dateLabel);
        headerCenter.add(left);
        headerCenter.add(right);
        header.add(headerCenter, BorderLayout.CENTER);
        setTable(currentDate, workersList);

    }
    public void refreshTable()
    {
        header.remove(header.getComponentCount() -1);
        this.removeAll();
        this.setTable(this.currentDate, workersList);
        this.revalidate();
        this.repaint();
    }
    private void setTable(Date date, List <Worker> workersList)
    {
        currentDate = date;
        this.workersList = workersList;
        Vector <String> columnNames = new Vector<String>();
        Vector<Vector> rowData = new Vector <Vector>();
        Vector<String> tColumnNames = new Vector<String>();
        Vector<Vector> tRowData = new Vector<Vector>();
        tColumnNames.addElement(" ");
        if(!dayMode)
        {
            if(workersList.isEmpty())
                workersSelectionBox = new JComboBox();
            else
            {
                workersSelectionBox = new JComboBox(workersList.toArray());
                workersSelectionBox.setSelectedIndex(selectedItemIndex);
            }
            workersSelectionBox.addItemListener(this);

            dateLabel.setText(currentDate.monthYearString());
            header.add(workersSelectionBox, BorderLayout.LINE_END);
            header.setBorder(new EmptyBorder(7,7,7,7));
            this.add(header,BorderLayout.PAGE_START);
            
            String firstDay = cal.getDayName(new Date(1, currentDate.getMonth(), currentDate.getYear()));
            int dayNumber = 0;
            for (String Day : cal.Days) {
                if (firstDay.equals(Day)) {
                    break;
                }
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
                    Vector<String> tData = new Vector<String>();
                    if(minute == 0)
                        minuteString = "00";
                    else
                        minuteString = Integer.toString(minute);

                    tData.addElement(" " + Integer.toString(hour) + ":" + minuteString);
                    for(int j = 0; j < cal.getAmountOfDays(currentDate.getMonth()); j++)
                        data.addElement("");
                    minute+=15;
                    rowData.addElement(data);
                    tRowData.addElement(tData);
                } 
            }
        }
        else
        {
            dateLabel.setText(cal.getDayName(selectedDay)+ " " + this.selectedDay.toString());
            header.setBorder(new EmptyBorder(7,7,7,7));
            this.add(header,BorderLayout.PAGE_START);
            
            for(Worker worker : workersList)
            {
                columnNames.addElement(worker.toString());
            }
            int minute;
            String minuteString;
            for(int hour = 9; hour< 19; hour++)
            {
                minute = 0;
                for(int i = 0; i < 4; i++)
                {
                    Vector<String> data = new Vector<String>();
                    Vector<String> tData = new Vector<String>();
                    if(minute == 0)
                        minuteString = "00";
                    else
                        minuteString = Integer.toString(minute);

                    tData.addElement(" " + Integer.toString(hour) + ":" + minuteString);
                    for(int j = 0; j < workersList.size(); j++)
                        data.addElement("");
                    minute+=15;
                    rowData.addElement(data);
                    tRowData.addElement(tData);
                } 
            }  
        }
        Vector<String> tData = new Vector<String>();
        tData.addElement(" 19:00");
        tRowData.addElement(tData);
        CustomTableModel cTable = new CustomTableModel(rowData, columnNames);
        CustomTableModel cTimeTable = new CustomTableModel(tRowData, tColumnNames);
        timeTable = new CustomTable(cTimeTable);
        table = new CustomTable(cTable);
        JScrollPane timeScrollPane = new JScrollPane(timeTable,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        timeScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0,0));

        timeScrollPane.setPreferredSize(new Dimension(50,300));
        timeTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        timeTable.setCellSelectionEnabled(false);
        timeTable.setShowGrid(false);
        timeTable.setRowHeight(20);
        timeTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        timeTable.getTableHeader().setResizingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        timeScrollPane.getVerticalScrollBar().setModel(scrollPane.getVerticalScrollBar().getModel());
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setCellSelectionEnabled(true);
        table.getSelectionModel().addListSelectionListener(this);
        table.getColumnModel().getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        table.setRowHeight(20);
        table.setShowGrid(false);
        table.getColumnModel().addColumnModelListener(this);
        table.setIntercellSpacing(new Dimension(0,0));
        table.getTableHeader().setReorderingAllowed(false);
        TableColumnAdjuster adjuster = new TableColumnAdjuster(table);
        
        adjuster.adjustColumns();
        table.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                switchModes(e);
            }
        });
        
        
        this.add(timeScrollPane, BorderLayout.LINE_START);
        this.add(scrollPane, BorderLayout.CENTER);
    }
    private void switchModes(MouseEvent e)
    {
        int col = table.columnAtPoint(e.getPoint());
        if(!dayMode)
        {
            selectedDay = new Date(col+1, currentDate.getMonth(), currentDate.getYear());
            header.remove(header.getComponentCount() -1);
            headerCenter.remove(0);
            header.remove(1);
            JPanel centerLabelPanel = new JPanel();
            centerLabelPanel.add(dateLabel);
            centerLabelPanel.setBorder(new EmptyBorder(5,0,0,0));
            centerLabelPanel.setBackground(header.getBackground());
            header.add(centerLabelPanel, BorderLayout.CENTER);
            header.add(headerCenter, BorderLayout.LINE_END);
        }
        else
        {
            headerCenter.add(dateLabel, 0);
            //header.remove(1);
            header.remove(1);
            header.add(headerCenter, BorderLayout.CENTER);
            selectedItemIndex = col;
            currentDate = selectedDay;
        }
        dayMode = !dayMode;

        removeAll();
        setTable(selectedDay, workersList);
        this.setWorkCells();
        revalidate();
        repaint();
    }
    @Override
    public void actionPerformed(ActionEvent ae) {
        Object source = ae.getSource();
        if(source == right)
        {
            if(!dayMode)
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
            else
            {
                this.removeAll();
                if(cal.getAmountOfDays(selectedDay.getMonth()) == selectedDay.getDay() && selectedDay.getMonth() == 12)
                    selectedDay = new Date(1, 1, selectedDay.getYear()+1);
                else if(cal.getAmountOfDays(selectedDay.getMonth()) == selectedDay.getDay())
                    selectedDay = new Date(1, selectedDay.getMonth()+1, selectedDay.getYear());
                else
                    selectedDay.setDay(selectedDay.getDay()+1);
                this.setTable(currentDate, workersList);
                this.revalidate();
                this.repaint();
            }
        }
        else if(source == left)
        {
            if(!dayMode)
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
            else
            {
                this.removeAll();
                if(selectedDay.getDay() == 1 && selectedDay.getMonth() == 1)
                    selectedDay = new Date(cal.getAmountOfDays(1, selectedDay.getYear()-1), 1, selectedDay.getYear()-1);
                else if(selectedDay.getDay() == 1)
                    selectedDay = new Date(cal.getAmountOfDays(selectedDay.getMonth()-1, selectedDay.getYear()), selectedDay.getMonth()-1, selectedDay.getYear());
                else
                    selectedDay.setDay(selectedDay.getDay()-1);
                this.setTable(currentDate, workersList);
                this.revalidate();
                this.repaint();
            }
        }
        else if(source == today)
        {
            if(!dayMode)
            {
                selectedItemIndex = workersSelectionBox.getSelectedIndex();
                header.remove(header.getComponentCount() -1);
                this.removeAll();
                this.setTable(new Date(), workersList);
                this.revalidate();
                this.repaint();
            }
            else
            {
                this.selectedDay = new Date();
                this.removeAll();
                this.setTable(new Date(), workersList);
                this.revalidate();
                this.repaint();
            }
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
    public void setWorkCells()
    {
        if(!dayMode)
        {
            selectedItemIndex = workersSelectionBox.getSelectedIndex();
            Worker tmp = workersList.get(selectedItemIndex);
            Map <Date, List<Work>> works = tmp.getWorksInMonth(currentDate.getMonth(), currentDate.getYear());
            if(!works.isEmpty())
            {
               for(Date key : works.keySet())
               {
                   if(works.get(key) != null)
                   {
                       List <Work> worksOnDate = works.get(key);
                       for(Work tmpWork : worksOnDate)
                       {
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
            }
        }
        else
        {
            int col = 0;
            for(Worker worker : workersList)
            {
                List<Work> worksOnDay = worker.getWorks(this.selectedDay);
                if(worksOnDay != null && !worksOnDay.isEmpty())
                {
                    for(Work work : worksOnDay)
                    {
                        List <Point> workCells = work.getCells();
                        List <Point> tmpWorkCells = new ArrayList<>();
                        for(Point cell : workCells)
                        {
                            tmpWorkCells.add(new Point(cell.x, col));
                        }
                        table.changeCellColor(tmpWorkCells, work.getColor(), Boolean.TRUE);
                        Point tmpPoint = (Point)work.getCells().get(0);
                        List<String> tmpSubstrings = substrings(work.getWorkDescription(), col, work.getCells().size());
                        int row = tmpPoint.x;
                        for(String substring : tmpSubstrings)
                        {
                           table.setValueAt(substring, row, col);
                           row++;
                        }
                    }
                }
                col++;
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
        //if(!dayMode)
            this.setWorkCells();
    }

    @Override
    public void columnSelectionChanged(ListSelectionEvent e) {
        this.valueChanged(e);
//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void valueChanged(ListSelectionEvent lse) {
        
        if(!lse.getValueIsAdjusting())
        {
            this.setWorkCells();
            int[] rows = table.getSelectedRows();
            int col = table.getSelectedColumn();
            List<Point> selected = new ArrayList<>();
            for(int tmpRow : rows)
            {
                selected.add(new Point(tmpRow,col));
            }
            if(!dayMode)
            {
                selectedItemIndex = workersSelectionBox.getSelectedIndex();
                Worker tmpWorker = workersList.get(selectedItemIndex);
                List<Work> works = tmpWorker.getWorks(new Date(col+1,currentDate.getMonth(), currentDate.getYear()));
                if(works != null && !works.isEmpty())
                {
                    for(Work tmpWork : works)
                    {
                        List <Point> tmpPoints = tmpWork.getCells();
                        
                        if(selected.size() > 1)
                        {

                            Set<Point> selectedSet = new HashSet<>(selected);
                            Set<Point> workCellsSet = new HashSet<>(tmpPoints);
                            if(selectedSet.stream().anyMatch(workCellsSet::contains))
                            {
                                table.clearSelection();
                                return;
                            }
                        }
                        else if(rows.length > 0 && tmpPoints.contains(new Point(rows[0],col)))
                        {
                            table.changeCellColor(tmpPoints, tmpWork.getColor().brighter(), Boolean.TRUE);

                        }
                    }
                }
            }
            else if(col >= 0)
            {
                Worker selectedWorker = workersList.get(col);
                List<Work> works = selectedWorker.getWorks(selectedDay);
                if(works != null && !works.isEmpty())
                {
                    for(Work tmpWork : works)
                    {
                        List <Point> tmpPoints = tmpWork.getCells();
                        List <Point> tmpWorkCells = new ArrayList<>();
                        for(Point cell : tmpPoints)
                        {
                            tmpWorkCells.add(new Point(cell.x, col));
                        }
                        if(selected.size() > 1)
                        {
                            Set<Point> selectedSet = new HashSet<>(selected);
                            Set<Point> workCellsSet = new HashSet<>(tmpWorkCells);
                            if(selectedSet.stream().anyMatch(workCellsSet::contains))
                            {
                                table.clearSelection();
                                return;
                            }
                        }
                        else if(rows.length > 0 && tmpWorkCells.contains(new Point(rows[0],col)))
                        {
                            table.changeCellColor(tmpWorkCells, tmpWork.getColor().brighter(), Boolean.TRUE);

                        }
                    }
                }
            }
        }
        
    }
}
