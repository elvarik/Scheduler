/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;

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
public class RightPanel extends JPanel implements ActionListener {
  
    private JTextArea annotations=new JTextArea();
    private JLabel annotationsLabel=new JLabel("Uwagi:");
    private JTextField race= new JTextField();
    private final JLabel raceLabel = new JLabel("Rasa psa:");
    private JTextField phoneNo=new JTextField();
    private final JLabel phoneNoLabel = new JLabel("Numer telefonu:");
    private JTextField dogName= new JTextField();
    private final JLabel dogNameLabel = new JLabel("Imię psa:");
    private JTextField clientName= new JTextField();
    private final JLabel clientNameLabel = new JLabel("Imię klienta:");
    private JTextField startTime = new JTextField();
    private final JLabel startTimeLabel = new JLabel("Rozpoczęcie pracy:");
    private JTextField endTime = new JTextField();
    private final JLabel endTimeLabel = new JLabel("Zakończenie pracy:");
    private JPanel centerPanel;
    private JPanel bottom;
    private JComboBox Later;//pamiętaj to, na potem
    private JPanel header;
    private JPanel medium;
    private JLabel emerge=new JLabel("");
    private JButton addButton=new JButton("Dodaj zlecenie");
    private TablePanel tablePanel;
    private List<Point> selectedCells;
    private TimeBoxHejHejHej starttimebox=new TimeBoxHejHejHej();
    private TimeBoxHejHejHej endtimebox=new TimeBoxHejHejHej();
 
    
    RightPanel(){
        super(new BorderLayout());
        
        centerPanel = new JPanel(new GridLayout(0,2,-40,10));
        medium = new JPanel(new GridLayout(0,2,-40,10));
        bottom = new JPanel(new GridLayout(0,2,-40,10));
        header = new JPanel(new BorderLayout());
        JLabel headerLabel = new JLabel("Zarządzanie");
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(headerLabel.getFont().deriveFont(18.f));
        emerge.setForeground(Color.WHITE);
        
        headerLabel.setBorder(new EmptyBorder(10,20,10,10));
        header.add(headerLabel,BorderLayout.LINE_START);
        header.setPreferredSize(new Dimension(0,47));
        header.setBackground(new Color(39, 60, 117).brighter());
        this.setBackground(new Color(43, 50, 60));
        medium.setBackground(this.getBackground());
        
        centerPanel.setBackground(this.getBackground());
        bottom.setBackground(this.getBackground());
        starttimebox.setBackground(this.getBackground());
        endtimebox.setBackground(this.getBackground());
        raceLabel.setForeground(Color.WHITE);
        dogNameLabel.setForeground(Color.WHITE);
        clientNameLabel.setForeground(Color.WHITE);
        phoneNoLabel.setForeground(Color.WHITE);
        annotationsLabel.setForeground(Color.WHITE);
        startTimeLabel.setForeground(Color.WHITE);
        endTimeLabel.setForeground(Color.WHITE);
        centerPanel.add(phoneNoLabel);

        centerPanel.add(phoneNo);
        
        centerPanel.add(clientNameLabel);

        centerPanel.add(clientName);
        
        centerPanel.add(raceLabel);
        race.setPreferredSize(new Dimension(150,20));
        centerPanel.add(race);

        centerPanel.add(dogNameLabel);
        
        centerPanel.add(dogName);
       
        
        medium.add(startTimeLabel);
        medium.add(starttimebox);
        medium.add(endTimeLabel);
        medium.add(endtimebox);
        
        bottom.add(annotationsLabel);
        annotations.setFont(dogName.getFont());
        annotations.setLineWrap(true);
        annotations.setWrapStyleWord(true);
        
        JScrollPane areaScrollPane = new JScrollPane(annotations);
        areaScrollPane.setPreferredSize(new Dimension(150,100));
        bottom.add(areaScrollPane);
        bottom.add(emerge);


        centerPanel.setBorder(new EmptyBorder(20,10,0,10));
        bottom.setBorder(new EmptyBorder(8,10,0,10));
        medium.setBorder(new EmptyBorder(8,10,0,10));
        JPanel gridWrapPanel = new JPanel();
        gridWrapPanel.add(centerPanel);
        gridWrapPanel.add(medium);
        gridWrapPanel.add(bottom);
        addButton.addActionListener(this);
        
        this.add(header, BorderLayout.PAGE_START);
        this.add(gridWrapPanel, BorderLayout.CENTER);
        JPanel buttonWrapper=new JPanel();
        buttonWrapper.setBackground(this.getBackground());
        addButton.setBackground(this.getBackground());
        buttonWrapper.setBorder(new EmptyBorder(10,10,20,10));
        buttonWrapper.add(addButton);
        
        this.add(buttonWrapper, BorderLayout.PAGE_END);

        gridWrapPanel.setBackground(this.getBackground());
    }
    
    public void setOppositePanel(TablePanel tablePanel)
    {
        this.tablePanel = tablePanel;
    }
    
    public void setRightEnabled(boolean check){
        for(Component x : centerPanel.getComponents() ){
            x.setEnabled(check);
        }
        annotations.setEnabled(check);
        starttimebox.hours.setEnabled(check);
        starttimebox.minutes.setEnabled(check);
        endtimebox.hours.setEnabled(check);
        endtimebox.minutes.setEnabled(check);
        }
    
    public boolean check(){
        if (!phoneNo.getText().matches("\\d+")){

            return false;
        }
        return true;

    }
    public void setSelectedCells(List<Point> cells)
    {
        this.selectedCells = cells;

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
                if(source==addButton){
                    if(check()==true){
                        this.emerge.setText("W porzo mordo");
                    }
                    else{
                        this.emerge.setText("Numer telefonu zawiera litery");
                    }
                    if(selectedCells != null)
                    {
                        tablePanel.addWork(null, selectedCells, header.getBackground(), annotations.getText());
                    }
                }
    }
}
