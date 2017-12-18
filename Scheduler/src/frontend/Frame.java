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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;


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
    public Frame(){
        
        super("Scheduler");
        //father.setBackground(new Color(206, 224, 218));
        //header.setBackground(new Color(206, 224, 218));
        setResizable(false);
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
        this.header.add(lewo);
        this.header.add(headerLabel);
        this.header.add(prawo);
        father.add(header);
        lewo.setFocusPainted(false);
        prawo.setFocusPainted(false);
        lewo.addActionListener(this);
        prawo.addActionListener(this);
        header.setBorder(new EmptyBorder(10,10,25,25));
        miesiąc = new Month(father.getBackground(), month, year);
        //miesiąc.reAssing(month, year);
        setComponents((int)((double)screenSize.height*0.6),(int)((double)screenSize.width*0.7));
        this.setupMenuBar();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        
        
        
        workers = new ArrayList<>();
        workers.add(new Worker("Piotr Filipkowski"));
        workers.add(new Worker("Marcin Gałecki"));
        
        
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
		Object source = e.getSource();
                if(source==lewo){
                    if(month == 1)
                    {
                        month= 12;
                        year--;
                    }
                    else
                        month--;
                    
                    father.remove(1);
                    headerLabel.setText(Cal.Months[month - 1]+" "+ year);
                    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                    setComponents((int)((double)screenSize.height*0.6),(int)((double)screenSize.width*0.7));
                    this.pack();
                    
                }
                else if(source==prawo){
                    if(month==12)
                    {
                        month = 1;
                        year++;
                    }
                    else
                        month++;
                    
                    father.remove(1);
                    headerLabel.setText(Cal.Months[month - 1]+" "+ year);
                    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                    setComponents((int)((double)screenSize.height*0.6),(int)((double)screenSize.width*0.7));
                    this.pack();
                    
                    
                }
    }

    private void setComponents(int h, int w) {
        
        
        GridBagLayout masterGridBag;
        header.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        masterGridBag = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();        
        masterGridBag.setConstraints(father, constraints);
       
        
        
        //miesiąc.setBackground(new Color(206, 224, 218));
        
        
        father.setLayout(masterGridBag);
        miesiąc.reAssing(month, year);
        
        
        

        //constraints.fill = GridBagConstraints.PAGE_START;
        constraints.anchor=GridBagConstraints.PAGE_START;
        constraints.weighty=0;
        constraints.ipadx = 0;
        constraints.ipady = 0;
        constraints.gridy = 1;
        constraints.gridx = 0;
        father.add(miesiąc,constraints);
        
       
        //this.add(father);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
