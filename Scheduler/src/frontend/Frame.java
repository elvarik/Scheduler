/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
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
        JLabel emptyLabel = new JLabel("");
        this.setPreferredSize(windowSize);

        JLabel Zobacz_asie=Asia.Visible;
        this.getContentPane().add(emptyLabel, BorderLayout.CENTER);
        this.getContentPane().add(Zobacz_asie, BorderLayout.CENTER);
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
    
}
