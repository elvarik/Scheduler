/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend;

import BackEnd.Worker;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Hardkor
 */
public class WorkersWindow extends JDialog implements ListSelectionListener,ActionListener, DocumentListener{
    private List<Worker> workers;
    private JPanel main;
    private JButton addButton;
    private JButton deleteButton;
    private JList workersList;
    private DefaultListModel listModel;
    private JTextField employeeName;
    private boolean onStartClear;
    private JPanel grid;
    private JPanel rightPanel;
    private JTextField nameField;
    private JTextField phoneField;
    private JTextField emailField;
    private JButton okButton;
    private JButton undoButton;
    private JPanel rightButtonPane;
    public WorkersWindow(JFrame father,List<Worker> workers)
    {
        super(father,"Pracownicy", true);
        this.workers = workers;
        grid = new JPanel(new GridLayout(0,2,-40,10));
        rightPanel = new JPanel(new BorderLayout());
        rightButtonPane = new JPanel();
        //grid.setLayout(new BoxLayout(grid,BoxLayout.PAGE_AXIS));
        main = new JPanel(new BorderLayout());
        onStartClear = true;
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        GenerateContent();
        this.setLocationRelativeTo(father);
        this.pack();
        setSize(600, 350);
        this.setVisible(true);
    }
    private void GenerateContent()
    {
        
        listModel = new DefaultListModel();
        for(Worker worker : workers)
            listModel.addElement(worker.toString());
        workersList = new JList(listModel);
        workersList.addListSelectionListener(this);
        workersList.setLayoutOrientation(JList.VERTICAL);
        //workersList.addListSelectionListener(this);
        workersList.setVisibleRowCount(-1);
        workersList.setFont(workersList.getFont().deriveFont(12.0f));
        DefaultListCellRenderer renderer = (DefaultListCellRenderer) workersList.getCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        JScrollPane listScrollPane = new JScrollPane(workersList);
        JPanel buttonPane = new JPanel();
        addButton = new JButton(" + ");
        addButton.addActionListener(this);
        addButton.setEnabled(false);
        deleteButton = new JButton(" - ");
        deleteButton.setEnabled(false);
        employeeName = new JTextField("Podaj imie i nazwisko pracownika",20);
        
        employeeName.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                if(onStartClear)
                {
                    employeeName.setText("");
                    onStartClear = false;
                }
            }
        });
        employeeName.getDocument().addDocumentListener(this);
        deleteButton.addActionListener(this);
        buttonPane.add(employeeName);
        buttonPane.add(addButton);
        buttonPane.add(deleteButton);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        main.add(listScrollPane, BorderLayout.CENTER);
        main.add(buttonPane, BorderLayout.PAGE_END);
        main.setMinimumSize(new Dimension(300, 0));
        
        JLabel nameLabel = new JLabel("Imie i nazwisko:");
        nameField = new JTextField(15);
        nameField.getDocument().addDocumentListener(this);
        JPanel name = new JPanel(new FlowLayout());
        name.add(nameLabel);
        name.add(nameField);
        
        grid.add(nameLabel);
        grid.add(nameField);
        
        JLabel phoneLabel = new JLabel("Numer telefonu:");
        phoneField = new JTextField(15);
        JPanel phone = new JPanel(new FlowLayout());
        phone.add(phoneLabel);
        phone.add(phoneField);
        
        grid.add(phoneLabel);
        grid.add(phoneField);
        
        JLabel emailLabel = new JLabel("Adres e-mail:");
        emailField = new JTextField(15);
        JPanel email = new JPanel(new FlowLayout());
        email.add(emailLabel);
        email.add(emailField);
        grid.add(emailLabel);
        grid.add(emailField);
        
        okButton = new JButton("Zatwierdź zmiany");
        okButton.addActionListener(this);
        okButton.setEnabled(false);
        
        undoButton = new JButton("Cofnij zmiany");
        undoButton.addActionListener(this);
        undoButton.setEnabled(false);
        rightButtonPane.add(okButton);
        rightButtonPane.add(undoButton);
        
        grid.setBorder(new EmptyBorder(20,0,0,0));
        JPanel gridWrapPanel = new JPanel();
        gridWrapPanel.add(grid);
        rightPanel.add(gridWrapPanel,BorderLayout.CENTER);
        rightPanel.add(rightButtonPane, BorderLayout.PAGE_END);
        rightPanel.setBorder(new TitledBorder("Edycja pracownika"));
        
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,main,rightPanel);
        splitPane.setDividerLocation(300);
        this.add(splitPane);
    }

    @Override
    public void valueChanged(ListSelectionEvent lse) {
        if(workersList.getSelectedIndices().length > 0)
            deleteButton.setEnabled(true);
        else
            deleteButton.setEnabled(false);
        if(workersList.getSelectedIndex() >= 0)
        {
        nameField.setText(workers.get(workersList.getSelectedIndex()).toString());
        emailField.setText(workers.get(workersList.getSelectedIndex()).getEmail());
        phoneField.setText(workers.get(workersList.getSelectedIndex()).getPhoneNumber());
        okButton.setEnabled(true);
        undoButton.setEnabled(true);
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        Object source = ae.getSource();
        if(source == addButton)
        {
            workers.add(new Worker(employeeName.getText()));
            listModel.addElement(workers.get(workers.size()-1).toString());
            workersList.ensureIndexIsVisible(workers.size()-1);
            workersList.setSelectedIndex(workers.size()-1);
        }
        else if(source == deleteButton)
        {
            Object[] options = {"Tak", "Nie"};
            int message = JOptionPane.showOptionDialog(this,"Czy jesteś pewien, że chcesz usunąć?","Potwierdź",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,null, options, options[1]);
            int [] deleteIndex = workersList.getSelectedIndices();
            if(deleteIndex.length > 0 && message==JOptionPane.YES_OPTION)
            {
                int i = 0;
                for( int index : deleteIndex)
                {
                workers.remove(index - i);
                listModel.remove(index - i);
                workersList.ensureIndexIsVisible(index - i);
                i++;
                }
                nameField.setText("");
                emailField.setText("");
                phoneField.setText("");
            }
            
        }
        else if(source == okButton)
        {
            int selected = workersList.getSelectedIndex();
            workers.get(selected).setNameSurname(nameField.getText());
            workers.get(selected).setEmail(emailField.getText());
            workers.get(selected).setPhoneNumber(phoneField.getText());
            listModel.set(selected, workers.get(selected).toString());
            JOptionPane.showMessageDialog(this,"Zmiany zatwierdzono pomyślnie.", "", JOptionPane.INFORMATION_MESSAGE);
        }
        else if(source == undoButton)
        {
            int selected = workersList.getSelectedIndex();
            nameField.setText(workers.get(selected).toString());
            emailField.setText(workers.get(selected).getEmail());
            phoneField.setText(workers.get(selected).getPhoneNumber());
        }
        
        
    }

    @Override
    public void insertUpdate(DocumentEvent de) {
        addButton.setEnabled(true);
        if(workersList.getSelectedIndex() != -1)
        okButton.setEnabled(true);
    }

    @Override
    public void removeUpdate(DocumentEvent de) {
        if(employeeName.getText().equals(""))
            addButton.setEnabled(false);
        if(nameField.getText().equals(""))
            okButton.setEnabled(false);
    }

    @Override
    public void changedUpdate(DocumentEvent de) {
        
    }
    
    
}
