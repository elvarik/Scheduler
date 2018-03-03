/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.io.InputStream;
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
    private JTextField phoneNo= new JTextField();
    private final JLabel phoneNoLabel = new JLabel("Numer telefonu:");
    private JTextField dogName= new JTextField();
    private final JLabel dogNameLabel = new JLabel("Imię psa:");
    private JTextField startTime = new JTextField();
    private final JLabel startTimeLabel = new JLabel("Rozpoczęcie pracy");
    private JTextField endTime = new JTextField();
    private final JLabel endTimeLabel = new JLabel("Zakończenie pracy");
    private JPanel centerPanel;
    private JPanel bottom;
    private JPanel header;
    RightPanel(){
        super(new BorderLayout());
        
        centerPanel = new JPanel(new GridLayout(0,2,10,10));
        bottom = new JPanel(new GridLayout(0,2,10,10));
        header = new JPanel(new BorderLayout());
        JLabel headerLabel = new JLabel("Zarządzanie");
        headerLabel.setForeground(Color.WHITE);
        

        headerLabel.setBorder(new EmptyBorder(10,20,10,10));
        header.add(headerLabel,BorderLayout.LINE_START);
        header.setPreferredSize(new Dimension(0,47));
        header.setBackground(new Color(39, 60, 117).brighter());
        this.setBackground(new Color(43, 50, 60));
        
        centerPanel.setBackground(this.getBackground());
        bottom.setBackground(this.getBackground());
        raceLabel.setForeground(Color.WHITE);
        dogNameLabel.setForeground(Color.WHITE);
        phoneNoLabel.setForeground(Color.WHITE);
        annotationsLabel.setForeground(Color.WHITE);
        centerPanel.add(phoneNoLabel);

        centerPanel.add(phoneNo);
        
        centerPanel.add(raceLabel);
        race.setPreferredSize(new Dimension(130,20));
        centerPanel.add(race);

        centerPanel.add(dogNameLabel);

        centerPanel.add(dogName);
        

        bottom.add(annotationsLabel);
        annotations.setFont(dogName.getFont());
        annotations.setLineWrap(true);
        annotations.setWrapStyleWord(true);
        
        JScrollPane areaScrollPane = new JScrollPane(annotations);
        areaScrollPane.setPreferredSize(new Dimension(130,100));
        bottom.add(areaScrollPane);
        
        centerPanel.setBorder(new EmptyBorder(20,10,0,10));
        bottom.setBorder(new EmptyBorder(8,10,0,10));
        JPanel gridWrapPanel = new JPanel();
        gridWrapPanel.add(centerPanel);
        gridWrapPanel.add(bottom);
        this.add(header, BorderLayout.PAGE_START);
        this.add(gridWrapPanel, BorderLayout.CENTER);
        gridWrapPanel.setBackground(this.getBackground());
    }
}
