/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend;

import BackEnd.Customer;
import BackEnd.Time;
import BackEnd.Work;
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
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Pioty
 */
public class RightPanel extends JPanel implements ActionListener {
  
    private JTextArea annotations=new JTextArea();
    private JLabel annotationsLabel=new JLabel("Uwagi:");
    private JTextField race= new JTextField();
    private final JLabel raceLabel = new JLabel("Rasa psa:");
    private JTextField price= new JTextField();
    private final JLabel priceLabel = new JLabel("Cena:");
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
    private JButton deleteButton = new JButton("Usuń zlecenie");
    private TablePanel tablePanel;
    private TimeBoxHejHejHej startTimeBox=new TimeBoxHejHejHej();
    private TimeBoxHejHejHej endTimeBox=new TimeBoxHejHejHej();
     private JLabel cardPaidLabel=new JLabel("Czy płatne kartą:");
    private JRadioButton cardPaidYes = new JRadioButton("Tak");
    private JRadioButton cardPaidNo = new JRadioButton("Nie");
    private JPanel cardPanel= new JPanel(new GridLayout(0,2,0,0));
    private ButtonGroup cardPaidGroup=new ButtonGroup();
    private Color selectedWorkColor = null;
    private boolean editMode = false;
    private JPanel buttonWrapper;
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
        cardPaidNo.setSelected(true);
        headerLabel.setBorder(new EmptyBorder(10,20,10,10));
        header.add(headerLabel,BorderLayout.LINE_START);
        header.setPreferredSize(new Dimension(0,47));
        header.setBackground(new Color(39, 60, 117).brighter());
        this.setBackground(new Color(43, 50, 60));
        medium.setBackground(this.getBackground());
        cardPaidGroup.add(cardPaidYes);
        cardPaidGroup.add(cardPaidNo);
        centerPanel.setBackground(this.getBackground());
        bottom.setBackground(this.getBackground());
        startTimeBox.setBackground(this.getBackground());
        endTimeBox.setBackground(this.getBackground());
        cardPaidYes.setBackground(this.getBackground());
        cardPaidNo.setBackground(this.getBackground());
        cardPaidYes.setForeground(Color.WHITE);
        cardPaidNo.setForeground(Color.WHITE);
        raceLabel.setForeground(Color.WHITE);
        dogNameLabel.setForeground(Color.WHITE);
        clientNameLabel.setForeground(Color.WHITE);
        phoneNoLabel.setForeground(Color.WHITE);
        annotationsLabel.setForeground(Color.WHITE);
        startTimeLabel.setForeground(Color.WHITE);
        endTimeLabel.setForeground(Color.WHITE);
        priceLabel.setForeground(Color.WHITE);
        cardPaidLabel.setForeground(Color.WHITE);
        centerPanel.add(phoneNoLabel);

        centerPanel.add(phoneNo);
        
        centerPanel.add(clientNameLabel);

        centerPanel.add(clientName);
        
        centerPanel.add(raceLabel);
        race.setPreferredSize(new Dimension(150,20));
        centerPanel.add(race);

        centerPanel.add(dogNameLabel);
        
        centerPanel.add(dogName);
        centerPanel.add(priceLabel);
        centerPanel.add(price);
        centerPanel.add(cardPaidLabel);
        cardPanel.add(cardPaidYes);
        cardPanel.add(cardPaidNo);
        centerPanel.add(cardPanel);
       
        
        medium.add(startTimeLabel);
        medium.add(startTimeBox);
        medium.add(endTimeLabel);
        medium.add(endTimeBox);
        
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
        buttonWrapper = new JPanel();
        buttonWrapper.setBackground(this.getBackground());
        addButton.setBackground(this.getBackground());
        buttonWrapper.setBorder(new EmptyBorder(10,10,20,10));
        buttonWrapper.add(addButton);
        buttonWrapper.add(deleteButton);
        deleteButton.setVisible(false);
        deleteButton.addActionListener(this);
        this.add(buttonWrapper, BorderLayout.PAGE_END);
        setRightEnabled(false);
        gridWrapPanel.setBackground(this.getBackground());
        
        this.startTimeBox.up.addActionListener(this);
        this.startTimeBox.down.addActionListener(this);
        this.endTimeBox.up.addActionListener(this);
        this.endTimeBox.down.addActionListener(this);
        
        deleteButton.setBackground(this.getBackground());
        
        
        
        
    }
    
    public void setOppositePanel(TablePanel tablePanel)
    {
        this.tablePanel = tablePanel;
    }
    
    public void setRightEnabled(boolean check){
        for(Component x : centerPanel.getComponents() ){
            if(!(x instanceof JLabel)){
            x.setEnabled(check);
            }
        }
        annotations.setEnabled(check);
        startTimeBox.hours.setEnabled(check);
        startTimeBox.minutes.setEnabled(check);
        endTimeBox.hours.setEnabled(check);
        endTimeBox.minutes.setEnabled(check);
        cardPaidYes.setEnabled(check);
        cardPaidNo.setEnabled(check);
        addButton.setEnabled(check);
        startTimeBox.setEnabled(check);
        endTimeBox.setEnabled(check);
        deleteButton.setEnabled(check);
        if(this.endTimeBox.hours.getText().equals("19") && this.endTimeBox.minutes.getText().equals("00"))
                this.endTimeBox.up.setEnabled(false);
        if(this.startTimeBox.hours.getText().equals("9") && this.startTimeBox.minutes.getText().equals("00"))
                this.startTimeBox.down.setEnabled(false);
    }
    
    public boolean check(){
        if (!(phoneNo.getText().matches("\\d+") ||phoneNo.getText().matches(""))){

            return false;
        }
        return true;

    }

    public void setFieldsContent(String name,String dogName, String dogRace, String phoneNumber,String price, boolean payedByCard,String annotations, Time startTime, Time endTime)
    {
        this.clientName.setText(name);
        this.dogName.setText(dogName);
        this.race.setText(dogRace);
        this.phoneNo.setText(phoneNumber);
        this.price.setText(price);
        if(payedByCard) 
            this.cardPaidYes.setSelected(true);
        else
            this.cardPaidNo.setSelected(true);
        this.annotations.setText(annotations);
        this.startTimeBox.hours.setText(Integer.toString(startTime.getHour()));
        if(startTime.getMinute() == 0)
            this.startTimeBox.minutes.setText("00");
        else
            this.startTimeBox.minutes.setText(Integer.toString(startTime.getMinute()));
        this.endTimeBox.hours.setText(Integer.toString(endTime.getHour()));
        if(endTime.getMinute() == 0)
            this.endTimeBox.minutes.setText("00");
        else
            this.endTimeBox.minutes.setText(Integer.toString(endTime.getMinute()));
    }
    public void setFieldsContent(Work work)
    {
        Customer customer = work.getCustomer();
        Time startTime = work.getStartTime();
        Time endTime = work.getEndTime();
        this.clientName.setText(customer.getName());
        this.dogName.setText(customer.getDogName());
        this.race.setText(customer.getDogRace());
        this.phoneNo.setText(customer.getPhoneNumber());
        this.price.setText(customer.getPrice());
        if(customer.isPayedByCard()) 
            this.cardPaidYes.setSelected(true);
        else
            this.cardPaidNo.setSelected(true);
        this.annotations.setText(work.getWorkDescription());
        this.startTimeBox.hours.setText(Integer.toString(startTime.getHour()));
        if(startTime.getMinute() == 0)
            this.startTimeBox.minutes.setText("00");
        else
            this.startTimeBox.minutes.setText(Integer.toString(startTime.getMinute()));
        this.endTimeBox.hours.setText(Integer.toString(endTime.getHour()));
        if(endTime.getMinute() == 0)
            this.endTimeBox.minutes.setText("00");
        else
            this.endTimeBox.minutes.setText(Integer.toString(endTime.getMinute()));
        this.selectedWorkColor = work.getColor();
    }
    public void setEditMode(boolean editMode)
    {
        this.editMode = editMode;
        if(editMode)
        {
            addButton.setText("Edytuj zlecenie");
            
            deleteButton.setVisible(true);
        }
        else
        {
            addButton.setText("Dodaj zlecenie");
            if(buttonWrapper.getComponentCount() > 1)
            {
                deleteButton.setVisible(false);
            }
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(source==addButton){
//            if(check()==true){
//                this.emerge.setText("W porzo mordo");
//            }
//            else{
//                this.emerge.setText("Numer telefonu zawiera litery");
//            }
            if(editMode)
            {
                Customer customer = new Customer(clientName.getText(), dogName.getText(), race.getText(), phoneNo.getText(),price.getText() ,cardPaidYes.isSelected());
                tablePanel.editWork(null, selectedWorkColor, annotations.getText(), customer, this.startTimeBox.convertToTime(), this.endTimeBox.convertToTime());
            }
            else
            {
                Customer customer = new Customer(clientName.getText(), dogName.getText(), race.getText(), phoneNo.getText(),price.getText() ,cardPaidYes.isSelected());
                tablePanel.addWork(null, header.getBackground(), annotations.getText(), customer);
            }
        }
        if(source == this.startTimeBox.down)
        {
            
            if(editMode)
                this.startTimeBox.decMinute();
            else
                tablePanel.incSelectionTop();
                //this.startTimeBox.decMinute();
            if(this.startTimeBox.hours.getText().equals("9") && this.startTimeBox.minutes.getText().equals("00"))
                this.startTimeBox.down.setEnabled(false);
            else
                this.startTimeBox.down.setEnabled(true);
        }
        else if(source == this.startTimeBox.up)
        {
            if(editMode)
                this.startTimeBox.incMinute();
            else
                tablePanel.decSelectionTop();
                //this.startTimeBox.incMinute();
            if(this.startTimeBox.hours.getText().equals("9") && this.startTimeBox.minutes.getText().equals("00"))
                this.startTimeBox.down.setEnabled(false);
            else
                this.startTimeBox.down.setEnabled(true);
        }
        else if(source == this.endTimeBox.up)
        {
            if(editMode)
                this.endTimeBox.decMinute();
            else
                tablePanel.incSelectionBot();
            if(this.endTimeBox.hours.getText().equals("19") && this.endTimeBox.minutes.getText().equals("00"))
                this.endTimeBox.up.setEnabled(false);
            else
                this.endTimeBox.up.setEnabled(true);
            
        }
        else if(source == this.endTimeBox.down)
        {
            if(editMode)
                this.endTimeBox.incMinute();
            else
            tablePanel.decSelectionBot();
            
            if(this.endTimeBox.hours.getText().equals("19") && this.endTimeBox.minutes.getText().equals("00"))
                this.endTimeBox.up.setEnabled(false);
            else
                this.endTimeBox.up.setEnabled(true);
        }
        else if(source == deleteButton)
        {
            tablePanel.deleteWork();
        }
    }
}
