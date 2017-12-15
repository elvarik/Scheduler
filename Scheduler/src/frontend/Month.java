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
    int x=200;//długość dnia
    int y=40;//wysokość dnia
    CalendarPlotter Cal=new CalendarPlotter();
    public Month(int h,int w){
        this.h=h;
        this.w=w;
        GridBagLayout GridBag;
        GridBag = new GridBagLayout();
        int number=Cal.getAmountOfDays()+1;
        GridBagConstraints c = new GridBagConstraints();        
        GridBag.setConstraints(this, c);
        int licznik=0;
        int length=w/x;
        int height=h/y;
        System.out.println(Integer.toString(height)+" "+Integer.toString(length));
        
        for (int j=0;j<height;j++){
        for (int i=0;i<length;i++){
        if(licznik<number){
        JButton guzik=new JButton(Integer.toString(licznik));
        c.fill = GridBagConstraints.HORIZONTAL;
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
