/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend;

import BackEnd.CalendarPlotter;
import BackEnd.ColumnMouseListener;
import BackEnd.CustomTable;
import BackEnd.CustomTableModel;
import BackEnd.Customer;
import BackEnd.Date;
import BackEnd.HeaderCellRenderer;
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
import BackEnd.TableColumnAdjuster;
import BackEnd.Time;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableCellRenderer;
/**
 *
 * @author Hardkor
 */
public class TablePanel extends JPanel implements ActionListener, ItemListener, TableColumnModelListener, ListSelectionListener, KeyListener{
    
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
    private JPopupMenu popupMenu;
    private JMenuItem deleteMenuButton;
    private JMenuItem colorBlue,colorGreen, colorViolet;
    private JMenu colorsMenu;
    private int rowAtPoint,colAtPoint;
    private RightPanel rightPanel;
    private JPanel headerLeft;
    private JScrollPane scrollPane;
    private Work selectedWork = null;
    public boolean selectedByRight = false;
    private Date dayToday;
    private Map<String, Customer> customers;
    public TablePanel(List <Worker> workersList, Date date)
    {
        super(new BorderLayout());
        customers = new HashMap<>();
        popupMenu = new JPopupMenu();
        popupMenu.setPreferredSize(new Dimension(100,50));
        colorsMenu = new JMenu("Kolor");
        colorBlue = new JMenuItem("");
        colorBlue.setOpaque(true);
        colorBlue.setBackground(new Color(39, 60, 117).brighter());
        colorGreen = new JMenuItem("");
        colorGreen.setOpaque(true);
        colorGreen.setBackground(new Color(17, 155, 26));
        
        colorViolet = new JMenuItem("");
        colorViolet.setOpaque(true);
        colorViolet.setBackground(new Color(138, 3, 150));
        
        colorBlue.addActionListener(this);
        colorGreen.addActionListener(this);
        colorViolet.addActionListener(this);
        colorsMenu.add(colorBlue);
        colorsMenu.add(colorGreen);
        colorsMenu.add(colorViolet);
        
        deleteMenuButton = new JMenuItem("Usuń");
        deleteMenuButton.addActionListener(this);
        popupMenu.add(colorsMenu);
        popupMenu.add(deleteMenuButton);
        today = new JButton("Dzisiaj");
        left = new JButton("◄");
        right = new JButton("►");
        left.addActionListener(this);
        right.addActionListener(this);
        today.addActionListener(this);
        header = new JPanel(new BorderLayout());
        
        headerCenter = new JPanel();
        headerLeft = new JPanel();
        header.setBackground(new Color(39, 60, 117).brighter());
        headerCenter.setBackground(header.getBackground());
        headerLeft.setBackground(header.getBackground());
        left.setBackground(header.getBackground());
        right.setBackground(header.getBackground());
        today.setBackground(header.getBackground());
        cal = new CalendarPlotter();
        
        currentDate = date;
        this.workersList = workersList;
        dateLabel = new JLabel(date.monthYearString());
        dateLabel.setForeground(Color.WHITE);
        headerLeft.add(today);
        headerLeft.add(left);
        headerLeft.add(right);
        header.add(headerLeft, BorderLayout.LINE_START);
        //today.setPreferredSize(new Dimension(70,30));
        headerCenter.add(dateLabel);
        //headerCenter.add(left);
        //headerCenter.add(right);
        header.add(headerCenter, BorderLayout.CENTER);
        dateLabel.setFont(dateLabel.getFont().deriveFont(18.f));
        dayToday = new Date(currentDate);
        setTable(currentDate, workersList);
        deleteMenuButton.setEnabled(false);
        colorsMenu.setEnabled(false);
        
        this.scrollToDay(currentDate.getDay());
        //this.setCurrentDayHeader();
        for(Worker worker : workersList)
        {
            Date [] workDates = worker.getWorksDates();
            for(Date dateKey : workDates)
            {
                List <Work> works = worker.getWorks(dateKey);
                for(Work tmpWork : works)
                {
                    customers.put(tmpWork.getCustomer().getPhoneNumber(), tmpWork.getCustomer());
                }
            }
        }
        
        
    }
    public void setCurrentDayHeader()
    {
        if(dayToday.getMonth() == currentDate.getMonth() && dayToday.getYear() == currentDate.getYear())
        {
            table.getColumnModel().getColumn(dayToday.getDay()-1).setHeaderRenderer(new HeaderCellRenderer(header.getBackground()));
            table.getTableHeader().addMouseListener(new ColumnMouseListener(table,header.getBackground(),new Color(43, 50, 60).brighter(), dayToday.getDay()-1));
            table.getTableHeader().addMouseMotionListener(new ColumnMouseListener(table,header.getBackground(),new Color(43, 50, 60).brighter(), dayToday.getDay()-1));
        }
    }
    public void refreshTable()
    {
        if(!dayMode)
            header.remove(header.getComponentCount() -1);
        this.removeAll();
        this.setTable(this.currentDate, workersList);
        this.revalidate();
        this.repaint();
        
    }
    public void refreshWithScroll()
    {
        Point scrollPos = scrollPane.getViewport().getViewPosition();
        
        this.refreshTable();
        scrollPane.getViewport().setViewPosition(scrollPos);
    }
    private void scrollToDay(int day)
    {
        int scrollPosX = 0;
        for(int i = 0; i < day-1; i++)
        {
            scrollPosX +=table.getColumnModel().getColumn(i).getWidth();
        }
        
        Point scrollPos = new Point(scrollPosX, 0);
        scrollPane.getViewport().setViewPosition(scrollPos);
    }
    public void setOppositePanel(RightPanel rightPanel)
    {
        this.rightPanel = rightPanel;
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
        scrollPane = new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
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
        table.setComponentPopupMenu(popupMenu);
        popupMenu.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        int[] rows = table.getSelectedRows();
                        int col = table.getSelectedColumn();
                        List<Point> selected = new ArrayList<>();
                        if(col > -1 && rows.length >=1)
                        {
                            for(int row : rows)
                                selected.add(new Point(row,col));
                        }
                        
                        rowAtPoint = table.rowAtPoint(SwingUtilities.convertPoint(popupMenu, new Point(0, 0), table));
                        colAtPoint = table.columnAtPoint(SwingUtilities.convertPoint(popupMenu, new Point(0, 0), table));
                        if (rowAtPoint > -1 && !selected.contains(new Point(rowAtPoint,colAtPoint))) {
                            table.setColumnSelectionInterval(colAtPoint, colAtPoint);
                            table.setRowSelectionInterval(rowAtPoint, rowAtPoint);
                        }
                        rightPanel.setRightEnabled(false);
                    }
                });
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {
                rightPanel.setRightEnabled(true);

            }
        });
        adjuster.adjustColumns();
        table.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                switchModes(e);
            }
        });
        
        table.addKeyListener(this);
        this.add(timeScrollPane, BorderLayout.LINE_START);
        this.add(scrollPane, BorderLayout.CENTER);
        if(!dayMode)
            this.setCurrentDayHeader();
    }
    public void addWork(Worker worker, Color color, String workDescription, Customer customer, String dogName, String dogRace, boolean payedByCard, String price)
    {
        int[] rows = table.getSelectedRows();
        int col = table.getSelectedColumn();
        List<Point> cells = new ArrayList<>();
        for(int tmpRow : rows)
        {
            cells.add(new Point(tmpRow,col));
        }
        String timeString = timeTable.getValueAt(cells.get(0).x, 0).toString();
        timeString = timeString.substring(1, timeString.length());
        Time startTime = new Time(timeString);
        timeString = timeTable.getValueAt(cells.get(cells.size()-1).x+1, 0).toString();
        timeString = timeString.substring(1, timeString.length());
        Time endTime = new Time(timeString);
        if(worker == null)
        {
            Worker tmpWorker;
            if(!dayMode)
               tmpWorker = workersList.get(workersSelectionBox.getSelectedIndex());
            else
            {
                tmpWorker = workersList.get(cells.get(0).y);
                List<Point> tmpCells = new ArrayList<>();
                for(Point cell : cells)
                    tmpCells.add(new Point(cell.x, selectedDay.getDay()-1));
                cells = tmpCells;
            }
            
            Date workDate = new Date(cells.get(0).y+1, currentDate.getMonth(), currentDate.getYear());
            Work newWork = new Work(workDate, cells, startTime, endTime,workDescription,color, dogName, dogRace, payedByCard, price);
            Customer tmpCustomer = customers.get(customer.getPhoneNumber());
            if(tmpCustomer == null)
            {
                newWork.setCustomer(customer);
                customers.put(customer.getPhoneNumber(), customer);
                List<String> phoneNumbers = new ArrayList<String>(customers.keySet());
                rightPanel.setPhoneContent(phoneNumbers);
            }
            else
            {
                newWork.setCustomer(tmpCustomer);
                tmpCustomer.setName(customer.getName());
            }
            tmpWorker.putWork(workDate, newWork);
        }
        rightPanel.setRightEnabled(false);
        this.refreshWithScroll();
    }
    public void editWork(Worker worker, Color color, String workDescription, Customer customer, Time startTime, Time endTime, String dogName, String dogRace, boolean payedByCard, String price)
    {
        int row = table.getSelectedRow();
        int col = table.getSelectedColumn();
        Point selected = new Point(row, col);
        if(dayMode)
            selected = new Point(row, selectedDay.getDay()-1);
        if(worker == null && this.selectedWork != null)
        {
            
            Worker tmpWorker = null; 
            List<Work> workList = null;
            if(!dayMode)
            {
               tmpWorker = workersList.get(workersSelectionBox.getSelectedIndex());
               workList= tmpWorker.getWorks(new Date(col+1, currentDate.getMonth(), currentDate.getYear()));
            }
            else
            {
                tmpWorker = workersList.get(col);
                workList = tmpWorker.getWorks(selectedDay);
            }
            Work toRemove = null;
            Work edited = null;
            for(Work tmpWork : workList)
            {
                List<Point> workCells = tmpWork.getCells();
                if(workCells.contains(selected))
                {
                    int startRow = this.getRowFromTimeTable(startTime);
                    int endRow = this.getRowFromTimeTable(endTime) -1;
                    if(startRow == workCells.get(0).x && endRow == workCells.get(workCells.size()-1).x+1)
                    {
                        edited = new Work(tmpWork.getDate(), workCells, startTime, endTime, workDescription, color, dogName, dogRace, payedByCard, price);
                        toRemove = tmpWork;
                    }
                    else if(startRow < workCells.get(0).x)
                    {
                        while(true)
                        {
                            Point newCell = new Point(startRow, workCells.get(0).y);
                            if(workCells.contains(newCell))
                                break;
                            else
                                workCells.add(newCell);
                            startRow++;
                        }
                        workCells.sort((Point p1, Point p2)-> p1.x-p2.x);
                        
                    }
                    else if(startRow > workCells.get(0).x)
                    {
                        
                        Point newCell = new Point(startRow, workCells.get(0).y);
                        while(!workCells.isEmpty() && !workCells.get(0).equals(newCell))
                            workCells.remove(0);
                        
                    }
                    if(workCells.isEmpty())
                    {
                        Point newCell = new Point(startRow, col);
                        workCells.add(newCell);
                        while(true)
                        {
                            Point tmpCell = new Point(endRow, workCells.get(0).y);
                            if(workCells.contains(tmpCell))
                                break;
                            else
                                workCells.add(tmpCell);
                            endRow--;
                        }
                        workCells.sort((Point p1, Point p2)-> p1.x-p2.x);
                    }
                    else if(endRow > workCells.get(workCells.size()-1).x)
                    {
                        while(true)
                        {
                            Point newCell = new Point(endRow, workCells.get(0).y);
                            if(workCells.contains(newCell))
                                break;
                            else
                                workCells.add(newCell);
                            endRow--;
                        }
                        workCells.sort((Point p1, Point p2)-> p1.x-p2.x);
                    }
                    else if(endRow < workCells.get(workCells.size()-1).x)
                    {
                        Point newCell = new Point(endRow, workCells.get(0).y);
                        while(!workCells.get(workCells.size()-1).equals(newCell))
                            workCells.remove(workCells.size()-1);
                    }
                    edited = new Work(tmpWork.getDate(), workCells, startTime, endTime, workDescription, color, dogName, dogRace, payedByCard, price);
                    Customer tmpCustomer = customers.get(customer.getPhoneNumber());
                    if(tmpCustomer == null)
                    {
                        edited.setCustomer(customer);
                        customers.put(customer.getPhoneNumber(), customer);
                    }
                    else
                    {
                        tmpCustomer.setName(customer.getName());
                        edited.setCustomer(tmpCustomer);
                    }
                    toRemove = tmpWork;  
                    workList.remove(toRemove);
                    edited.getCustomer().removeWork(toRemove);
                    workList.add(edited);
                    break;
                }
            }
            this.refreshWithScroll();
            

            rightPanel.setRightEnabled(false);
            rightPanel.setEditMode(false);
        }
    }
    public void deleteWork()
    {
        int col = table.getSelectedColumn();
        int[] rows = table.getSelectedRows();
        List<Point> selected = new ArrayList<>();
        boolean multipleDelete = false;
        for(int tmpRow : rows)
        {
            selected.add(new Point(tmpRow,col));
        }
        Worker tmpWorker;
        List<Work> worksOnDay;
        if(!dayMode)
        {
            tmpWorker = workersList.get(selectedItemIndex);
            worksOnDay = tmpWorker.getWorks(new Date(col+1,currentDate.getMonth(), currentDate.getYear()));;
        }
        else
        {
            tmpWorker = workersList.get(col);
            worksOnDay = tmpWorker.getWorks(selectedDay);
        }
        List<Work> toDelete = new ArrayList<>();
        int message = JOptionPane.NO_OPTION;
        if(worksOnDay != null && !worksOnDay.isEmpty())
        {
            for(Work work: worksOnDay)
            {

                List<Point> workCells = work.getCells();
                List<Point> tmpWorkCells = null;
                if(!dayMode)
                {
                    if(rows.length>1)
                    {
                        
                        Set<Point> selectedSet = new HashSet<>(selected);
                        Set<Point> workCellsSet = new HashSet<>(workCells);
                        if(selectedSet.stream().anyMatch(workCellsSet::contains))
                        {
                            
                            toDelete.add(work);
                            multipleDelete = true;
                        }
                    }
                    else
                    {
                        if (rows[0] > -1 && workCells.contains(new Point(rows[0],col))) 
                        {
                            Object[] options = {"Tak", "Nie"};
                            message = JOptionPane.showOptionDialog(this,"Czy jesteś pewien, że chcesz usunąć? Zmiany są nieodwracalne.","Potwierdź",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,null, options, options[1]);
                            if(message == JOptionPane.YES_OPTION)
                                toDelete.add(work);
                        }
                    }
                }
                else
                {
                    tmpWorkCells = new ArrayList<>();
                    for(Point cell : workCells)
                    {
                        tmpWorkCells.add(new Point(cell.x, col));
                    }
                    if(rows.length>1)
                    {
                        
                        Set<Point> selectedSet = new HashSet<>(selected);
                        Set<Point> workCellsSet = new HashSet<>(tmpWorkCells);
                        if(selectedSet.stream().anyMatch(workCellsSet::contains))
                        {
                            
                            toDelete.add(work);
                            multipleDelete = true;
                        }
                    }
                    else
                    {
                        if (rows[0] > -1 && tmpWorkCells.contains(new Point(rows[0],col))) 
                        {
                            Object[] options = {"Tak", "Nie"};
                            message = JOptionPane.showOptionDialog(this,"Czy jesteś pewien, że chcesz usunąć? Zmiany są nieodwracalne.","Potwierdź",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,null, options, options[1]);
                            if(message == JOptionPane.YES_OPTION)
                                toDelete.add(work);
                        }
                    }
                }
            }
            if(multipleDelete)
            {
                Object[] options = {"Tak", "Nie"};
                message = JOptionPane.showOptionDialog(this,"Czy jesteś pewien, że chcesz usunąć? Zmiany są nieodwracalne.","Potwierdź",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,null, options, options[1]);
                if(message == JOptionPane.YES_OPTION)
                {
                    for(Work work : toDelete)
                    {
                        work.getCustomer().removeWork(work);
                        worksOnDay.remove(work);
                    }
                }
            }
            else
            {
                for(Work work : toDelete)
                {
                    work.getCustomer().removeWork(work);
                    worksOnDay.remove(work);
                }
            }
            if(message == JOptionPane.YES_OPTION)
            {
                refreshWithScroll();
                rightPanel.setEditMode(false);
                rightPanel.setRightEnabled(false);
            }
        } 
    }
    private void switchModes(MouseEvent e)
    {
        int col = table.columnAtPoint(e.getPoint());
        if(!dayMode)
        {
            selectedDay = new Date(col+1, currentDate.getMonth(), currentDate.getYear());
            header.remove(header.getComponentCount() -1);

            JPanel centerLabelPanel = new JPanel();
            centerLabelPanel.add(dateLabel);
            centerLabelPanel.setBorder(new EmptyBorder(1,0,0,0));
            centerLabelPanel.setBackground(header.getBackground());
            header.add(centerLabelPanel, BorderLayout.CENTER);
            header.add(headerCenter, BorderLayout.LINE_END);
        }
        else
        {
            headerCenter.add(dateLabel, 0);
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
        if(!dayMode)
            this.scrollToDay(currentDate.getDay());
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
                this.scrollToDay(currentDate.getDay());
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
        else if(source == deleteMenuButton)
        {
            this.deleteWork();
        }
        else if(source == colorBlue || source == colorGreen || source == colorViolet)
        {
            int col = table.getSelectedColumn();
            Worker tmpWorker;
            List<Work> worksOnDay;
            if(!dayMode)
            {
                tmpWorker = workersList.get(selectedItemIndex);
                worksOnDay = tmpWorker.getWorks(new Date(col+1,currentDate.getMonth(), currentDate.getYear()));;
            }
            else
            {
                tmpWorker = workersList.get(col);
                worksOnDay = tmpWorker.getWorks(selectedDay);
            }
            if(worksOnDay != null && !worksOnDay.isEmpty())
            {
                for(Work work: worksOnDay)
                {
                    
                    List<Point> workCells = work.getCells();
                    List<Point> tmpWorkCells = null;
                    if(!dayMode)
                    {
                        if (rowAtPoint > -1 && workCells.contains(new Point(rowAtPoint,colAtPoint))) 
                        {
                            work.setColor(((JMenuItem)source).getBackground());
                        }
                    }
                    else
                    {
                        tmpWorkCells = new ArrayList<>();
                        for(Point cell : workCells)
                        {
                            tmpWorkCells.add(new Point(cell.x, col));
                        }
                        if (rowAtPoint > -1 && tmpWorkCells.contains(new Point(rowAtPoint,colAtPoint))) 
                        {
                            work.setColor(((JMenuItem)source).getBackground());
                        }
                    }
                }
                this.refreshWithScroll();
                
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
        if(!dayMode)
        {
            header.remove(header.getComponentCount() -1);
            if(workersList.isEmpty())
                workersSelectionBox = new JComboBox();
            else
            {
                workersSelectionBox = new JComboBox(workersList.toArray());
                workersSelectionBox.setSelectedIndex(0);
                selectedItemIndex = 0;
            }
            workersSelectionBox.addItemListener(this);
            header.add(workersSelectionBox, BorderLayout.LINE_END);
        }
        header.revalidate();
        header.repaint();
    }
    public void setWorkCells()
    {  
        if(!dayMode &&!workersList.isEmpty())
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
                           String stringValue = tmpWork.getCustomer().getName() + " \n " + tmpWork.getDogName() + " - " + tmpWork.getDogRace();
                           List<String> tmpSubstrings = substrings(stringValue, tmpPoint.y, tmpWork.getCells().size());
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
        else if(!workersList.isEmpty())
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
                            table.setValueAt("", cell.x, col);
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
        table.repaint();
        
    }
    private List<String> substrings(String source, int column, int cellsAmount)
    {
        List<String> strings = new ArrayList<>();
        int width = table.getColumnModel().getColumn(column).getWidth()/5 + 1;
//        if(width > source.length())
//        {
//            strings.add(" " + source);
//            return strings;
//        }
        String line = " ";
        String [] words = source.split(" ");
        
        for(String word : words)
        {
            if(strings.size() < cellsAmount -1)
            {
                String tmp = line+word + " ";
                if(word.contains("\n"))
                {
                    strings.add(line);
                    //strings.add("");
                    line=" ";
                }
                else if(tmp.length() < width)
                    line = tmp;
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
        this.refreshWithScroll();
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
    private Time getTimeFromTable(int row)
    {
        String timeString = timeTable.getValueAt(row, 0).toString();
        timeString = timeString.substring(1, timeString.length());
        Time startTime = new Time(timeString);
        return startTime;
    }
    private int getRowFromTimeTable(Time time)
    {
        String timeString = " "+time.toString();
        for(int row = 0; row < timeTable.getRowCount(); row++)
        {
            String timeFromTable = (String) timeTable.getModel().getValueAt(row, 0);
            if(timeFromTable.equals(timeString))
                return row;
        }
        return -1;
    }
    @Override
    public void valueChanged(ListSelectionEvent lse) {
        
        if(!lse.getValueIsAdjusting() && !workersList.isEmpty())
        {
            selectedWork = null;
            rightPanel.setEditMode(false);
            this.setWorkCells();
            int[] rows = table.getSelectedRows();
            int col = table.getSelectedColumn();
            List<Point> selected = new ArrayList<>();
            boolean selectedWork = false;
            boolean selectedWorks = false;
            for(int tmpRow : rows)
            {
                selected.add(new Point(tmpRow,col));
            }
            if(!selectedByRight && rows.length > 0)
            {
                Time start = this.getTimeFromTable(rows[0]);
                Time end = this.getTimeFromTable(rows[rows.length-1]+1);
                rightPanel.setFieldsContent("", "", "", "", "", false,"", start, end);
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
                                table.changeCellColor(tmpPoints, tmpWork.getColor().brighter(), Boolean.TRUE);
                                selectedWorks = true;  
                            }
                            
                        }
                        else if(rows.length > 0 && tmpPoints.contains(new Point(rows[0],col)))
                        {
                            Customer customer = tmpWork.getCustomer();
                            selectedWork = true;
                            table.changeCellColor(tmpPoints, tmpWork.getColor().brighter(), Boolean.TRUE);
                            rightPanel.setFieldsContent(tmpWork);
                            this.selectedWork = tmpWork;
                            break;
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
                                table.changeCellColor(tmpWorkCells, tmpWork.getColor().brighter(), Boolean.TRUE);
                                selectedWorks = true;
                                table.repaint();
                            }
                        }
                        else if(rows.length > 0 && tmpWorkCells.contains(new Point(rows[0],col)))
                        {
                            Customer customer = tmpWork.getCustomer();
                            table.changeCellColor(tmpWorkCells, tmpWork.getColor().brighter(), Boolean.TRUE);
                            selectedWork= true;
                            rightPanel.setFieldsContent(tmpWork);
                            table.repaint();
                            this.selectedWork = tmpWork;
                            break;
                        }
                    }
                }
            }
            rightPanel.setRightEnabled(!(selectedWorks));
            rightPanel.setEditMode(selectedWork);
            deleteMenuButton.setEnabled(selectedWork || selectedWorks);
            colorsMenu.setEnabled(selectedWork);
            
        }
        
    }
    public boolean incSelectionTop()
    {
        int [] rows = table.getSelectedRows();
        int col = table.getSelectedColumn();
        if(rows[0] >0)
        {
            table.setColumnSelectionInterval(col, col);
            table.setRowSelectionInterval(rows[0]-1, rows[rows.length-1]);
            return true;
        }
        else
            return false;
    }
    public boolean decSelectionTop()
    {
        int [] rows = table.getSelectedRows();
        int col = table.getSelectedColumn();
        
        table.setColumnSelectionInterval(col, col);
        table.setRowSelectionInterval(rows[0]+1, rows[rows.length-1]);
        return true; 
    }
    public boolean incSelectionBot()
    {
        int [] rows = table.getSelectedRows();
        int col = table.getSelectedColumn();
        table.setColumnSelectionInterval(col, col);
        table.setRowSelectionInterval(rows[0], rows[rows.length-1]+1);
        return true;
    }
    public boolean decSelectionBot()
    {
        int [] rows = table.getSelectedRows();
        int col = table.getSelectedColumn();
        table.setColumnSelectionInterval(col, col);
        table.setRowSelectionInterval(rows[0], rows[rows.length-1]-1);
        return true;
    }
    public void assistRowSelection(int startRow, int endRow, int col)
    {
        Runnable doAssist = new Runnable(){
            @Override
            public void run() {
                table.setColumnSelectionInterval(col, col);
                table.setRowSelectionInterval(startRow, endRow);
            }
            
        };
        SwingUtilities.invokeLater(doAssist);
    }
    public void setSelection(Time start, Time end)
    {
        int col = table.getSelectedColumn();
        int startRow = this.getRowFromTimeTable(start);
        int endRow = this.getRowFromTimeTable(end)-1;
        if(startRow != -1 && endRow != -1)
        {
            this.selectedByRight = true;
            this.assistRowSelection(startRow, endRow, col);
        }
    }
    public Map<String, Customer> getCustomers()
    {
        return this.customers;
    }
            
    @Override
    public void keyTyped(KeyEvent ke) {
        
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        if(ke.getKeyCode() == 127)
            this.deleteWork();
    }

    @Override
    public void keyReleased(KeyEvent ke) {
    }

}
