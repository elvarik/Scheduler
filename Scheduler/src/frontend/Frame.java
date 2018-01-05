/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend;

import BackEnd.CalendarPlotter;
import BackEnd.Worker;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import BackEnd.CustomTable;
import BackEnd.CustomTableModel;
import javax.swing.JComboBox;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;

/**
 *
 * @author ptfil_000
 */
public class Frame extends JFrame implements KeyListener,ActionListener{
    protected WorkerLayout Asia=new WorkerLayout();
    protected JButton lewo=new JButton(" < ");
    protected JButton prawo=new JButton(" > ");
    protected JPanel father=(JPanel)this.getContentPane();
    private JLabel headerLabel;
    private List<Worker> workers;
    private int month, year;
    private Month miesiąc;
    CalendarPlotter Cal=new CalendarPlotter();
    JPanel header=new JPanel();
    JPanel left = new JPanel(new BorderLayout());
    JPanel right = new JPanel();
    public Frame(){
        
        super("Scheduler");
        //father.setBackground(new Color(206, 224, 218));
        //header.setBackground(new Color(206, 224, 218));
        //setResizable(false);
        addKeyListener(this);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
        }
        
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        //Dimension windowSize=new Dimension((int)((double)screenSize.width*0.50), (int)((double)screenSize.height*0.8));
       month = Cal.getCurrentMonth();
       year = Cal.getCurrentYear();
        //this.setPreferredSize(windowSize);
        headerLabel = new JLabel(Cal.Months[month - 1]+" "+year);
        this.header.add(headerLabel);
        this.header.add(lewo);
        this.header.add(prawo);
        father.add(header);
        lewo.setFocusPainted(false);
        prawo.setFocusPainted(false);
        lewo.addActionListener(this);
        prawo.addActionListener(this);
        header.setBorder(new EmptyBorder(10,10,10,10));
       
        //miesiąc.reAssing(month, year);
        workers = new ArrayList<>();
        workers.add(new Worker("Piotr Filipkowski"));
        workers.add(new Worker("Marcin Gałecki"));
        this.setPreferredSize(new Dimension(1000,700));
        setComponents((int)((double)screenSize.height*0.6),(int)((double)screenSize.width*0.7));
        this.setupMenuBar();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        
    }
    
    
    private void setupMenuBar()
    {
        JMenuBar menubar = new JMenuBar();
      
        JMenu file = new JMenu("Menu");
        file.setMnemonic(KeyEvent.VK_F);

        JMenuItem MenuItem1 = new JMenuItem("Wyjdź");
        MenuItem1.setMnemonic(KeyEvent.VK_W);
        MenuItem1.setToolTipText("Wyjdź z aplikacji");
        MenuItem1.addActionListener((ActionEvent event) -> {
            System.exit(0);
        });
        JMenuItem MenuItem2 = new JMenuItem("Zarządzaj pracownikami");
        MenuItem2.addActionListener((ActionEvent event) -> {
            this.okienko();
        });
        MenuItem2.setToolTipText("Dodaj lub usuń pracownika");
        file.add(MenuItem2);
        file.add(MenuItem1);

        menubar.add(file);

        setJMenuBar(menubar);
    }
    
    
    
    public void okienko(){
//        JFrame pracownicy=new JFrame();
//        Toolkit kit = Toolkit.getDefaultToolkit();
//        Dimension screenSize = kit.getScreenSize();
//        Dimension windowSize=new Dimension((int)((double)screenSize.width*0.5), (int)((double)screenSize.height*0.5));
//        JLabel emptyLabel = new JLabel("Pracownicy");
//        pracownicy.setPreferredSize(windowSize);
//        pracownicy.getContentPane().add(emptyLabel, BorderLayout.PAGE_START);
//        pracownicy.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        pracownicy.pack();
//        pracownicy.setLocationRelativeTo(null);
//        pracownicy.setVisible(true);
           List<Worker> tempWorkers = new ArrayList<>();
           tempWorkers.addAll(workers);
          WorkersWindow workersWindow = new WorkersWindow(this,tempWorkers);
          workers = tempWorkers;
    }
    @Override
    public void keyTyped(KeyEvent ke) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
    }

    private void setComponents(int h, int w) {
        
        JComboBox workersSelectionBox = new JComboBox(workers.toArray());
        workersSelectionBox.setSelectedIndex(0);
        header.add(workersSelectionBox);
        left.add(header,BorderLayout.PAGE_START);
        Vector <String> columnNames = new Vector<String>();
        Vector<Vector> rowData = new Vector <Vector>();
        
        columnNames.addElement("");
        for(int i = 0; i < 7; i++)
        {
            columnNames.addElement(Cal.Days[i]);
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
                for(int j = 0; j < 7; j++)
                    data.addElement("");
                minute+=15;
                rowData.addElement(data);
            } 
        }
        CustomTableModel cTable = new CustomTableModel(rowData, columnNames);

        CustomTable table = new CustomTable(cTable);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        table.setCellSelectionEnabled(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.setRowHeight(20);
        table.changeCellsColor(1, 4, 3, new Color(147, 197, 255));
        //table.changeCellColor(1, 1, Color.yellow);
        //table.changeCellColor(2, 1, Color.yellow);
        left.add(scrollPane,BorderLayout.CENTER);
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left, right);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(600);
        this.add(splitPane);
//        GridBagLayout masterGridBag;
//        header.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
//        masterGridBag = new GridBagLayout();
//        GridBagConstraints constraints = new GridBagConstraints();        
//        masterGridBag.setConstraints(father, constraints);
//       
//        
//        
//        //miesiąc.setBackground(new Color(206, 224, 218));
//        
//        
//        father.setLayout(masterGridBag);
//        miesiąc.reAssing(month, year);
//        
//        
//        
//
//        //constraints.fill = GridBagConstraints.PAGE_START;
//        constraints.anchor=GridBagConstraints.PAGE_START;
//        constraints.weighty=0;
//        constraints.ipadx = 0;
//        constraints.ipady = 0;
//        constraints.gridy = 1;
//        constraints.gridx = 0;
//        father.add(miesiąc,constraints);
        
       
        //this.add(father);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
        
    }

    
}
