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
    private JTextField dogName= new JTextField();
    private final JLabel dogNameLabel = new JLabel("Imię psa:");
    private JTextField startTime = new JTextField();
    private final JLabel startTimeLabel = new JLabel("Rozpoczęcie pracy");
    private JTextField endTime = new JTextField();
    private final JLabel endTimeLabel = new JLabel("Zakończenie pracy");
    private JPanel centerPanel;
    private JPanel header;
    private GridBagConstraints c;
    RightPanel(){
        super(new BorderLayout());
        
        centerPanel = new JPanel();
        header = new JPanel(new BorderLayout());
        JLabel headerLabel = new JLabel("Zarządzanie");
        headerLabel.setForeground(Color.WHITE);
        
        InputStream is = RightPanel.class.getResourceAsStream("Nunito-Regular.ttf");
        headerLabel.setFont(headerLabel.getFont().deriveFont(22.f));

        
        
        headerLabel.setBorder(new EmptyBorder(10,20,10,10));
        header.add(headerLabel,BorderLayout.LINE_START);
        header.setPreferredSize(new Dimension(0,47));
        header.setBackground(new Color(39, 60, 117).brighter());
        this.setBackground(new Color(43, 50, 60));
        centerPanel.setBackground(this.getBackground());
        raceLabel.setForeground(Color.WHITE);
        dogNameLabel.setForeground(Color.WHITE);
        annotationsLabel.setForeground(Color.WHITE);
        GridBagLayout g = new GridBagLayout();
        centerPanel.setLayout(g);
        c = new GridBagConstraints();
        g.setConstraints(centerPanel, c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx=1;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(5,0,5,0);  
        centerPanel.add(raceLabel,c);
        c.gridx = 1;
        c.gridy = 0;
        centerPanel.add(race,c);

        c.gridx = 0;
        c.gridy = 1;
        centerPanel.add(dogNameLabel,c);

        c.gridx = 1;
        c.gridy = 1;
        centerPanel.add(dogName,c);

        c.gridx = 0;
        c.gridy = 2;
        centerPanel.add(annotationsLabel,c);

        c.gridx = 1;
        c.gridy = 2;
        annotations.setFont(dogName.getFont());
        annotations.setLineWrap(true);
        annotations.setWrapStyleWord(true);
        JScrollPane areaScrollPane = new JScrollPane(annotations);

        c.gridheight= 2;
        c.ipady=100;
        centerPanel.add(areaScrollPane,c);
        this.add(header, BorderLayout.PAGE_START);
        this.add(centerPanel, BorderLayout.CENTER);
        centerPanel.setBorder(new EmptyBorder(50,50,50,50));
    }
}
