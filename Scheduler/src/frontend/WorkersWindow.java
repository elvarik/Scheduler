/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend;

import BackEnd.Worker;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Hardkor
 */
public class WorkersWindow extends JFrame implements ListSelectionListener,ActionListener, DocumentListener{
    private List<Worker> workers;
    private JPanel main;
    private JButton addButton;
    private JButton deleteButton;
    private JList workersList;
    private DefaultListModel listModel;
    private JTextField employeeName;
    public WorkersWindow(JFrame father,List<Worker> workers)
    {
        super("Pracownicy");
        this.workers = workers;
        main = new JPanel(new BorderLayout());
        
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        GenerateContent();
        this.setLocationRelativeTo(father);
        this.pack();
        setSize(300, 300);
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
        employeeName = new JTextField(15);
        employeeName.addActionListener(this);
        employeeName.getDocument().addDocumentListener(this);
        deleteButton.addActionListener(this);
        buttonPane.add(employeeName);
        buttonPane.add(addButton);
        buttonPane.add(deleteButton);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        main.add(listScrollPane, BorderLayout.CENTER);
        main.add(buttonPane, BorderLayout.PAGE_END);
        this.add(main);
    }

    @Override
    public void valueChanged(ListSelectionEvent lse) {
        int deleteIndex = workersList.getSelectedIndex();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        Object source = ae.getSource();
        if(source == addButton)
        {
            workers.add(new Worker(employeeName.getText()));
            listModel.addElement(workers.get(workers.size()-1).toString());
            workersList.ensureIndexIsVisible(workers.size()-1);
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
            }
        }
        
        
    }

    @Override
    public void insertUpdate(DocumentEvent de) {
        addButton.setEnabled(true);
    }

    @Override
    public void removeUpdate(DocumentEvent de) {
        if(employeeName.getText().equals(""))
            addButton.setEnabled(false);
    }

    @Override
    public void changedUpdate(DocumentEvent de) {
        
    }
    
    
}
