/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


/**
 *
 * @author ptfil_000
 */
public class Frame extends JFrame implements KeyListener{
    protected int RozmiarX=10;
    protected int RozmiarY=10;
    protected WorkerLayout Asia=new WorkerLayout();
    public Frame(){
        super("Dog Scheduler");
        addKeyListener(this);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
        }
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension windowSize=new Dimension((int)((double)screenSize.width*0.8), (int)((double)screenSize.height*0.8));
       
        this.setPreferredSize(windowSize);
        setComponents((int)((double)screenSize.height*0.6),(int)((double)screenSize.width*0.6));
        
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
        JFrame pracownicy=new JFrame();
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        Dimension windowSize=new Dimension((int)((double)screenSize.width*0.5), (int)((double)screenSize.height*0.5));
        JLabel emptyLabel = new JLabel("Pracownicy");
        pracownicy.setPreferredSize(windowSize);
        pracownicy.getContentPane().add(emptyLabel, BorderLayout.NORTH);
        pracownicy.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pracownicy.pack();
        pracownicy.setLocationRelativeTo(null);
        pracownicy.setVisible(true);
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

    private void setComponents(int h, int w) {
        GridBagLayout masterGridBag;
        JPanel father=new JPanel();
        masterGridBag = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();        
        masterGridBag.setConstraints(father, constraints);
        JButton lewo=new JButton("<<<");
        JButton prawo=new JButton(">>>");
        JLabel Zobacz_asie=Asia.Month;
        JLabel emptyLabel = new JLabel("gej");
        JLabel Zobacz_asie1=Asia.Day;
        Month miesiąc=new Month(h,w);
        
        //this.getContentPane().add(lewo, BorderLayout.CENTER);
        
        father.setLayout(masterGridBag);
        constraints.fill = GridBagConstraints.WEST;
        constraints.ipadx=0;
        constraints.ipady=h;
        constraints.gridx = 0;
        constraints.gridy = 0;
        father.add(lewo,constraints);
        
        //this.setBackground(new Color(179,184, 254));
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx =0;
        constraints.gridy = 0;
        constraints.gridx = 1;
        father.add(miesiąc,constraints);
        
        constraints.fill = GridBagConstraints.EAST;
        constraints.ipadx=0;
        constraints.gridx = 2;
        constraints.gridy = 0;
        father.add(prawo,constraints);
        
        this.add(father);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
