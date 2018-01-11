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
import BackEnd.Date;
import BackEnd.Time;
import BackEnd.Work;
import java.awt.Point;
import java.util.Map;
import javax.swing.JComboBox;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;

/**
 *
 * @author ptfil_000
 */
public class Frame extends JFrame implements KeyListener,ActionListener{
    protected WorkerLayout Asia=new WorkerLayout();
    protected JPanel father=(JPanel)this.getContentPane();
    private List<Worker> workers;
    private Month miesiąc;
    CalendarPlotter Cal=new CalendarPlotter();
    TablePanel leftPanel;
    RightPanel right;
    public Frame(){
        
        super("Scheduler");
        addKeyListener(this);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
        }
        
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        workers = new ArrayList<>();
        right= new RightPanel();
        
        List <Point> cells = new ArrayList<>();
        List <Point> cells2 = new ArrayList<>();
        
        cells.add(new Point(1,1));
        cells.add(new Point(2,1));
        cells2.add(new Point(3,4));
        cells2.add(new Point(4,4));
        cells2.add(new Point(5,4));
        Work praca = new Work(cells, new Time(9,15), new Time(9,45),"Chujowa praca", new Color(103, 160, 252));
        Work praca2 = new Work(cells2, new Time(9,45), new Time(10,30), "Fajniutka praca", new Color(111, 224, 96));        
                
        workers.add(new Worker("Piotr Filipkowski"));
        workers.get(0).putWork(new Date(), praca);
        workers.add(new Worker("Marcin Gałecki"));
        workers.get(1).putWork(new Date(4, 1, 2018), praca2);
        this.setPreferredSize(new Dimension(1000,700));
        setComponents();
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
           List<Worker> tempWorkers = new ArrayList<>();
           tempWorkers.addAll(workers);
          WorkersWindow workersWindow = new WorkersWindow(this,tempWorkers);
          workers = tempWorkers;
          leftPanel.setWorkers(workers);
          
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

    private void setComponents() {

        leftPanel = new TablePanel(workers, new Date());
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, right);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(600);
        this.add(splitPane);

    }

    
}
