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
import BackEnd.JTextFieldLimit;
import BackEnd.Time;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
/**
 *
 * @author Pioty
 */
public class TimeBoxHejHejHej extends JPanel implements ActionListener,DocumentListener{
     JTextField hours= new JTextField();;
    JTextField minutes= new JTextField();;
    private JLabel doubledot=new JLabel(":");
    private int changeValue = 15;
    JButton up= new JButton ("↑");
    JButton down=new JButton("↓");
    private JPanel arrowPanel;
    TimeBoxHejHejHej(){
       // super();
        arrowPanel = new JPanel(new GridLayout(0,1,0,0));
        arrowPanel.setPreferredSize(new Dimension(40,30));
        //this.setBorder(new EmptyBorder(0,0,0,0));
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
        minutes.getDocument().addDocumentListener(this);
        hours.getDocument().addDocumentListener(this);
    }

    public void incMinute()
    {
        Time time = new Time(this.toString());
        time.add(changeValue);
            this.hours.setText(Integer.toString(time.getHour()));
            if(time.getMinute() == 0)
                this.minutes.setText("00");
            else
                this.minutes.setText(Integer.toString(time.getMinute()));
    }
    public void decMinute()
    {
        Time time = new Time(this.toString());
        time.substract(changeValue);
        this.hours.setText(Integer.toString(time.getHour()));
        if(time.getMinute() == 0)
            this.minutes.setText("00");
        else
            this.minutes.setText(Integer.toString(time.getMinute()));
    }
    public Time convertToTime()
    {
        return new Time(this.toString());
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        
    }
    @Override
    public void setEnabled(boolean enabled)
    {
        up.setEnabled(enabled);
        down.setEnabled(enabled);
    }
    @Override
    public String toString() {
        return hours.getText()+":"+minutes.getText();
    }

    private void assistMinuteText(String text)
    {
        Runnable doAssist = new Runnable() {
            @Override
            public void run() {
                minutes.setText(text);
            }
        };
        SwingUtilities.invokeLater(doAssist);
    }
    private void assistHourText(String text)
    {
        Runnable doAssist = new Runnable() {
            @Override
            public void run() {
                hours.setText(text);
            }
        };
        SwingUtilities.invokeLater(doAssist);
    }
    @Override
    public void insertUpdate(DocumentEvent de) {
        if(minutes.getText().length() ==2)
        {
            int minute = Integer.parseInt(minutes.getText());
            if(minute%changeValue != 0 || minute == 60)
            {
                if(minute <= 45)
                {
                    int multipiler = minute / 15;
                    int bottom = changeValue*multipiler;
                    int top = changeValue*(multipiler+1);
                    int deltaTop = top - minute;
                    int deltaBot = minute - bottom;
                    int chuj = 0;
                    if(deltaBot > deltaTop)
                        this.assistMinuteText(Integer.toString(top));
                    else
                        this.assistMinuteText(Integer.toString(bottom));
                }
                else
                {
                    this.assistMinuteText("45");
                }
                
            } 
        }
        else if(minutes.getText().equals("0"))
        {
            this.assistMinuteText("00");
        }
        if(hours.getText().length() == 2)
        {
            if(Integer.parseInt(hours.getText()) > 24)
                this.assistHourText("24");
            else if(Integer.parseInt(hours.getText()) < 9)
                this.assistHourText("9");
        }
    }

    @Override
    public void removeUpdate(DocumentEvent de) {
        
    }

    @Override
    public void changedUpdate(DocumentEvent de) {
       
    }
    
}
