/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend;

import BackEnd.CalendarPlotter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Pioty
 */
public class Day extends JPanel implements MouseListener{
   
    JLabel etykieta=new JLabel("");
    CalendarPlotter Cal=new CalendarPlotter();
    boolean taken=false;
    public Day(String a, Color background){
        etykieta.setText(a);
        this.add(etykieta);
        addMouseListener(this);
        this.setBackground(new Color(215, 226, 223));
        this.setBorder(BorderFactory.createLineBorder(background, 3));
    }
    
    public void okienko(){
        JFrame pracownicy=new JFrame();
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        Dimension windowSize=new Dimension((int)((double)screenSize.width*0.5), (int)((double)screenSize.height*0.5));
        JLabel emptyLabel = new JLabel(this.etykieta.getText()+" "+Cal.Months[Cal.getCurrentMonth()-1]);
        pracownicy.setPreferredSize(windowSize);
        pracownicy.getContentPane().add(emptyLabel, BorderLayout.NORTH);
        pracownicy.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pracownicy.pack();
        pracownicy.setLocationRelativeTo(null);
        pracownicy.setVisible(true);
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
    this.setBackground(new Color(255, 155, 25));
    this.okienko();
    this.taken=true;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //this.setBackground(Color.red);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //this.setBackground(Color.red);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if(!taken){
        this.setBackground(new Color(196, 206, 203));
        }
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if(!taken){
        this.setBackground(new Color(215, 226, 223));
        }
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
