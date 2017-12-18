/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend;

import BackEnd.CalendarPlotter;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Pioty
 */
public class Month extends JPanel {
    
    Frame ramka;
    int CurrentMonth=0;
    Color background;
    int h=0;
    int w=0;
    int x=100;//długość dnia
    int y=100;//wysokość dnia
    JPanel parentPanel=new JPanel();
    CalendarPlotter Cal=new CalendarPlotter();
    JLabel etykieta=new JLabel("");
    private int monthIndex;
    public Month(){
        
    }
    public Month(int h,int w, Color background, int monthIndex){
        this.h=h;
        this.w=w;
        this.background = background;
        this.monthIndex = monthIndex;
        compose(h,w);
        this.add(parentPanel);
        this.CurrentMonth=Cal.getCurrentMonth();
        }
    
    public void compose(int h,int w){
        int licznik=1;
        int length=w/x;
        int height=h/y;
        int number=Cal.getAmountOfDays(monthIndex)+1;
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();    
        GridBagLayout GridBag;
        GridBag = new GridBagLayout();
        GridBag.setConstraints(parentPanel, c);
        System.out.println(Integer.toString(height)+" "+Integer.toString(length));
        c.gridx = 0;
        c.gridy = 0;
        int g=0;
        
        for (int j=1;j<=number/7+1;j++){
        for (int i=0;i<7;i++){
            Day guzik=new Day(Integer.toString(licznik), background);
            if(licznik<number){
            c.fill = GridBagConstraints.HORIZONTAL;
            c.ipady = 70;
            c.ipadx = 60;
            c.gridx = i;
            c.gridy = j;
            this.add(guzik,c);
            System.out.println(Integer.toString(i)+" "+Integer.toString(j));
            licznik++;
        }
        
        }
    }
    System.out.println(Integer.toString(g));
    }
}
