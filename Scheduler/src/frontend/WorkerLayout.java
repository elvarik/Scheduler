/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JPanel;
import BackEnd.CalendarPlotter;
import javax.swing.JLabel;
/**
 *
 * @author ptfil_000
 */
public class WorkerLayout extends JPanel implements  ActionListener, ItemListener {
    private String[] Months={"styczeń","luty","marzec","kwiecień","maj","czerwiec","lipiec","sierpień","wrzesień","październik","listopad","grudzień"};
    protected CalendarPlotter cal;
    int day=0;
    int month=0;
    protected JLabel Month=new JLabel("gej");
    protected JLabel Day= new JLabel("gej");
    public WorkerLayout(){   
        cal = new CalendarPlotter();
        month = cal.getCurrentMonth();
        day= cal.getCurrentDay();
        Month=new JLabel(Months[month - 1]);
        Day= new JLabel(Integer.toString(day));
    }
    @Override
    public void actionPerformed(ActionEvent ae) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void itemStateChanged(ItemEvent ie) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
