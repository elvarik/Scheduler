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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.BoundedRangeModel;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;


import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
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
        JFrame day=new JFrame();
        JScrollBar scrollBar = new JScrollBar();
        scrollBar.setUnitIncrement(2);
        scrollBar.setBlockIncrement(1);
        day.getContentPane().setLayout(new GridBagLayout());
        AdjustmentListener adjustmentListener = new AdjustmentListener() {
      public void adjustmentValueChanged(AdjustmentEvent adjustmentEvent) {
        System.out.println("Adjusted: " + adjustmentEvent.getValue());
      }
    };
        scrollBar.addAdjustmentListener(adjustmentListener);
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        Dimension windowSize=new Dimension((int)((double)screenSize.width*0.5), (int)((double)screenSize.height*0.5));
        JLabel emptyLabel = new JLabel(this.etykieta.getText()+" "+Cal.Months[Cal.getCurrentMonth()-1]);
        day.setPreferredSize(windowSize);
        day.getContentPane().setLayout(new BoxLayout(day.getContentPane(), BoxLayout.X_AXIS));
        //day.getContentPane().add(emptyLabel);
        
        loadDay(day,scrollBar);

        day.getContentPane().add(scrollBar, BorderLayout.LINE_END);
        
        
        day.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        day.pack();
        day.setLocationRelativeTo(null);
        day.setVisible(true);
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

    private void loadDay(JFrame day, JScrollBar b) {
        
        int licznik=1;
        int monthIndex =2;
        int year = 2017;
        int number=Cal.getAmountOfDays(monthIndex,year)+1;
        JPanel parentPanel=new JPanel();
        parentPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        GridBagLayout GridBag = new GridBagLayout();
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
            parentPanel.add(dayName,c);
            
        }
        int currentDay = 1;
        for (int j=1;j<=number/7+1;j++){
        for (int i=0;i<7;i++){
            if(counter>licznik){
                JPanel empty=new JPanel();
                if(licznik<number){
                    c.fill = GridBagConstraints.HORIZONTAL;
                    c.ipady = 70;
                    c.ipadx = 60;
                    c.gridx = i;
                    c.gridy = j+1;
                    parentPanel.add(empty,c);  
                }
            }
            else{
                JLabel guzik=new JLabel(Integer.toString(currentDay));
                guzik.setBackground(new Color(215, 226, 223));
                guzik.setBorder(BorderFactory.createLineBorder(new Color(215, 226, 223), 3));
                if(licznik<number){
                    c.fill = GridBagConstraints.HORIZONTAL;
                    c.ipady = 70;
                    c.ipadx = 60;
                    c.gridx = i;
                    c.gridy = j+1;
                    parentPanel.add(guzik,c);  
                }
                currentDay++;
            
            }
            licznik++;
        
        }
    }   

        day.getContentPane().add(parentPanel);
    }
    
    
    
}

