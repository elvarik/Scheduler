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
    private GridBagLayout GridBag;
    private GridBagConstraints c;
    private int monthIndex, year;
    public Month(){
        
    }
    public Month(Color background, int monthIndex, int year){
        
        this.h=h;
        this.w=w;
        this.background = background;
        this.monthIndex = monthIndex;
        this.year = year;
        setLayout(new GridBagLayout());
        compose();
        this.add(parentPanel);
        this.CurrentMonth=Cal.getCurrentMonth();
        }
    public void reAssing(int monthIndex, int year)
    {
        this.monthIndex = monthIndex;
        this.year = year;
        this.removeAll();
        compose();
    }
    public void compose(){
        int licznik=1;

        int number=Cal.getAmountOfDays(monthIndex,year)+1;
        
        c = new GridBagConstraints();
        GridBag = new GridBagLayout();
        GridBag.setConstraints(parentPanel, c);
        
        c.gridx = 0;
        c.gridy = 0;
        int g=0;
        String firstDay = Cal.getDayName(1, monthIndex, year);
        int counter = 0;
        while(true)
        {
            if(firstDay.equals(Cal.Days[counter]))
                break;
            counter++;
        }
        number+=counter;
        counter++;
        for(int i = 0; i < 7; i++)
        {
            JLabel dayName = new JLabel(Cal.Days[i]);
            //c.fill = GridBagConstraints.CENTER;
            c.gridx = i;
            c.gridy = 0;
            this.add(dayName,c);
            
        }
        int currentDay = 1;
        for (int j=1;j<=number/7+1;j++){
        for (int i=0;i<7;i++){
            if(counter>licznik){
                JPanel empty=new JPanel();
                if(licznik<number){
                    c.fill = GridBagConstraints.HORIZONTAL;
                    c.ipady = 20;
                    c.ipadx = 20;
                    c.gridx = i;
                    c.gridy = j+1;
                    this.add(empty,c);  
                }
            }
            else{
                Day guzik=new Day(Integer.toString(currentDay), background);
                if(licznik<number){
                    c.fill = GridBagConstraints.HORIZONTAL;
                    c.ipady = 20;
                    c.ipadx = 20;
                    c.gridx = i;
                    c.gridy = j+1;
                    this.add(guzik,c);  
                }
                currentDay++;
            
            }
            licznik++;
        
        }
    }
    }
}
