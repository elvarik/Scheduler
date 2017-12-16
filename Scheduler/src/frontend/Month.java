/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend;

import BackEnd.CalendarPlotter;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Pioty
 */
public class Month extends JPanel{
    int h=0;
    int w=0;
    int x=100;//długość dnia
    int y=20;//wysokość dnia
    JPanel parentPanel=new JPanel();
    CalendarPlotter Cal=new CalendarPlotter();
    public Month(int h,int w){
        this.h=h;
        this.w=w;
        compose(h,w);
        this.add(parentPanel);
        
        }
    
    public void compose(int h,int w){
         int licznik=1;
        int length=w/x;
        int height=h/y;
        int number=Cal.getAmountOfDays()+1;
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();    
        GridBagLayout GridBag;
        GridBag = new GridBagLayout();
        GridBag.setConstraints(parentPanel, c);
        System.out.println(Integer.toString(height)+" "+Integer.toString(length));
        for (int j=0;j<height;j++){
            
        for (int i=0;i<length;i++){
            JButton guzik=new JButton(Integer.toString(licznik));
        if(licznik<number){
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 40;
        c.ipadx = 30;
        c.gridx = i;
        c.gridy = j;
        this.add(guzik,c);
        System.out.println(Integer.toString(i)+" "+Integer.toString(j));
        licznik++;
        }
        
        }
    }
    
    }
}
