/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import BackEnd.JTextFieldLimit;
/**
 *
 * @author Pioty
 */
public class TimeBoxHejHejHej extends JPanel implements ActionListener{
     JTextField hours= new JTextField();;
    JTextField minutes= new JTextField();;
    private JLabel doubledot=new JLabel(":");
    private JButton up= new JButton ("↑");
    private JButton down=new JButton("↓");
    private JPanel arrowPanel;
    TimeBoxHejHejHej(){
       // super();
        arrowPanel = new JPanel(new GridLayout(0,1,0,0));
        arrowPanel.setPreferredSize(new Dimension(40,30));
        this.setBorder(new EmptyBorder(0,0,0,0));
        this.setPreferredSize(new Dimension(150,40));
        up.setPreferredSize(new Dimension(10,10));
        down.setPreferredSize(new Dimension(10,10));
        this.setBackground(new Color(43, 50, 60));
        arrowPanel.setBackground(new Color(43, 50, 60));
        up.setBackground(new Color(43, 50, 60));
        down.setBackground(new Color(43, 50, 60));
        arrowPanel.add(up);
        arrowPanel.add(down);
        hours.setPreferredSize(new Dimension(40,30));
        minutes.setPreferredSize(new Dimension(40,30));
        hours.setDocument(new JTextFieldLimit(2));
        minutes.setDocument(new JTextFieldLimit(2));
        doubledot.setForeground(Color.WHITE);
        up.addActionListener(this);
        down.addActionListener(this);
        this.add(hours);
        this.add(doubledot);
        this.add(minutes);
        this.add(arrowPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
