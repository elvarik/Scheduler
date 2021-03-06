/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend;

import BackEnd.CalendarPlotter;
import BackEnd.Worker;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import BackEnd.Date;
import BackEnd.ExcelExport;
import BackEnd.Time;
import BackEnd.Work;
import java.awt.Point;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JSplitPane;
import jxl.write.WriteException;

/**
 *
 * @author ptfil_000
 */
public class Frame extends JFrame implements ActionListener{
    protected WorkerLayout Asia=new WorkerLayout();
    protected JPanel father=(JPanel)this.getContentPane();
    private List<Worker> workers;
    private Month miesiąc;
    CalendarPlotter Cal=new CalendarPlotter();
    TablePanel leftPanel;
    RightPanel right;
    public Frame(List<Worker> workers){
        
        super("Scheduler");
        ImageIcon icon = new ImageIcon("newIcon.png");
        this.setIconImage(icon.getImage());
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
        }
        this.workers=workers;
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
//        List <Point> cells = new ArrayList<>();
//        List <Point> cells2 = new ArrayList<>();
//        List <Point> cells3 = new ArrayList<>();
//        List <Point> cells4 = new ArrayList<>();
//        cells.add(new Point(1,1));
//        cells.add(new Point(2,1));
//        cells2.add(new Point(3,1));
//        cells2.add(new Point(4,1));
//        cells2.add(new Point(5,1));
//        cells3.add(new Point(3,1));
//        cells3.add(new Point(4,1));
//        cells3.add(new Point(5,1));
//        cells4.add(new Point(3,3));
//        cells4.add(new Point(4,3));
//        cells4.add(new Point(5,3));
//        Work praca = new Work(new Date(2,1,2018), cells, new Time(9,15), new Time(9,45),"Chujowa praca", new Color(39, 60, 117).brighter());
//        Work praca2 = new Work(new Date(2, 1, 2018), cells2, new Time(9,45), new Time(10,30), "Fajniutka praca", new Color(17, 155, 26));        
//        Work praca3 = new Work(new Date(2,1,2018), cells3, new Time(9,45), new Time(10,30), "Ruchanie łysego psa jak sra, praca to życie", new Color(17, 155, 26));
//        Work praca4 = new Work(new Date(4,2,2018), cells4, new Time(9,45), new Time(10,30), "Praca jak praca, wymagające gówno", new Color(39, 60, 117).brighter());
//        workers.add(new Worker("Piotr Filipkowski"));
//        workers.get(0).putWork(praca.getDate(), praca);
//        workers.get(0).putWork(praca3.getDate(), praca3);
//        workers.add(new Worker("Marcin Gałecki"));
//        workers.get(1).putWork(praca2.getDate(), praca2);
//        workers.get(1).putWork(praca4.getDate(),praca4);
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
        JMenuItem MenuItem3 = new JMenuItem("Zapisz do Excela");
        MenuItem3.setMnemonic(KeyEvent.VK_S);
        JMenuItem MenuItem1 = new JMenuItem("Wyjdź");
        MenuItem1.setMnemonic(KeyEvent.VK_W);
        MenuItem1.setToolTipText("Wyjdź z aplikacji");
        MenuItem1.addActionListener((ActionEvent event) -> {
            System.exit(0);
        });
        MenuItem3.addActionListener((ActionEvent event) -> {
            try {
                ExcelExport a = new ExcelExport("lista_prac",workers, this);
            } catch (IOException ex) {
                Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (WriteException ex) {
                Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        JMenuItem MenuItem2 = new JMenuItem("Zarządzaj pracownikami");
        MenuItem2.addActionListener((ActionEvent event) -> {
            this.okienko();
        });
        MenuItem2.setToolTipText("Dodaj lub usuń pracownika");
        file.add(MenuItem2);
        file.add(MenuItem3);
        file.add(MenuItem1);

        menubar.add(file);

        setJMenuBar(menubar);
    }
      
    public void okienko(){
           
           
          WorkersWindow workersWindow = new WorkersWindow(this,workers);
          
          leftPanel.setWorkers(workers);
          leftPanel.refreshWithScroll();
          
    }
   
    @Override
    public void actionPerformed(ActionEvent e) {
        
    }
    
    private void setComponents() {

        leftPanel = new TablePanel(workers, new Date());
        right= new RightPanel(leftPanel);
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, right);
        
        leftPanel.setOppositePanel(right);
        //splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(600);
        this.add(splitPane);

    }

    
}
