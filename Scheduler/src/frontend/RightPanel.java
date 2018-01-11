/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Pioty
 */
public class RightPanel extends JPanel {
    private JTextArea annotations=new JTextArea();
    private JLabel annotationsLabel=new JLabel("Uwagi:");
    private JTextField race= new JTextField();
    private final JLabel raceLabel = new JLabel("Rasa psa:");
    private JTextField dogName= new JTextField();
    private final JLabel dogNameLabel = new JLabel("Imię psa:");
    private JTextField startTime = new JTextField();
    private final JLabel startTimeLabel = new JLabel("Rozpoczęcie pracy");
    private JTextField endTime = new JTextField();
    private final JLabel endTimeLabel = new JLabel("Zakończenie pracy");
    private GridBagConstraints c;
    RightPanel(){
        GridBagLayout  g=new GridBagLayout();
        this.setLayout(g);
        c = new GridBagConstraints();
        g.setConstraints(this, c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx=1;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(5,0,5,0);  
        this.add(raceLabel,c);
        c.gridx = 1;
        c.gridy = 0;
        this.add(race,c);

        c.gridx = 0;
        c.gridy = 1;
        this.add(dogNameLabel,c);

        c.gridx = 1;
        c.gridy = 1;
        this.add(dogName,c);

        c.gridx = 0;
        c.gridy = 2;
        this.add(annotationsLabel,c);

        c.gridx = 1;
        c.gridy = 2;
        annotations.setFont(dogName.getFont());
        annotations.setLineWrap(true);
        annotations.setWrapStyleWord(true);
        JScrollPane areaScrollPane = new JScrollPane(annotations);

        c.gridheight= 2;
        c.ipady=100;
        this.add(areaScrollPane,c);
        
        this.setBorder(new EmptyBorder(50,50,50,50));
    }
}
