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
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
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
public class WorkersWindow extends JDialog implements ListSelectionListener,ActionListener, DocumentListener, MouseListener{
    private List<Worker> workers;
    private JPanel main;
    private JButton addButton;
    private JButton deleteButton;
    private JList workersList;
    private DefaultListModel listModel;
    private boolean onStartClear;
    private JPanel grid;
    private JPanel rightPanel;
    private JTextField nameField;
    private JTextField phoneField;
    private JTextField emailField;
    private JButton okButton;
    private JButton undoButton;
    private JPanel rightButtonPane;
    private JPopupMenu popup;
    private JMenuItem copyItem;
    private JMenuItem pasteItem;
    private boolean editMode;
    private JTextField copyFrom;
    private JTextField pasteTo;
    public WorkersWindow(JFrame father,List<Worker> workers)
    {
        super(father,"Pracownicy", true);
        this.workers = workers;
        popup = new JPopupMenu();
        copyItem = new JMenuItem("Kopiuj");
        copyItem.addActionListener(this);
        pasteItem = new JMenuItem("Wklej");
        pasteItem.addActionListener(this);

        popup.add(copyItem);
        popup.add(pasteItem);
        
        editMode = false;
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
        addButton = new JButton("Dodaj pracownika");
        addButton.addActionListener(this);
        addButton.setEnabled(true);
        deleteButton = new JButton("Usuń pracownika");
        deleteButton.setEnabled(false);
        
        
        deleteButton.addActionListener(this);
        //buttonPane.add(employeeName);
        buttonPane.add(addButton);
        buttonPane.add(deleteButton);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        main.add(listScrollPane, BorderLayout.CENTER);
        main.add(buttonPane, BorderLayout.PAGE_END);
        main.setMinimumSize(new Dimension(300, 0));
        
        JLabel nameLabel = new JLabel("Imie i nazwisko:");
        
        nameField = new JTextField(15);
        nameField.getDocument().addDocumentListener(this);
        nameField.addMouseListener(this);
        grid.add(nameLabel);
        grid.add(nameField);
        
        JLabel phoneLabel = new JLabel("Numer telefonu:");
        phoneField = new JTextField(15);
        phoneField.addMouseListener(this);
        grid.add(phoneLabel);
        grid.add(phoneField);
        
        JLabel emailLabel = new JLabel("Adres e-mail:");
        emailField = new JTextField(15);
        emailField.addMouseListener(this);
        grid.add(emailLabel);
        grid.add(emailField);
        
        okButton = new JButton("Edytuj");
        okButton.addActionListener(this);
        okButton.setEnabled(false);
        
        undoButton = new JButton("Cofnij");
        undoButton.addActionListener(this);
        undoButton.setEnabled(false);
        rightButtonPane.add(okButton);
        rightButtonPane.add(undoButton);
        
        grid.setBorder(new EmptyBorder(20,0,0,0));
        JPanel gridWrapPanel = new JPanel();
        gridWrapPanel.add(grid);
        undoButton.setVisible(false);
        rightPanel.add(gridWrapPanel,BorderLayout.CENTER);
        rightPanel.add(rightButtonPane, BorderLayout.PAGE_END);
        rightPanel.setBorder(new TitledBorder("Dane pracownika"));
        nameField.setEnabled(false);
        emailField.setEnabled(false);
        phoneField.setEnabled(false);
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,main,rightPanel);
        splitPane.setDividerLocation(300);
        this.add(splitPane);
    }
    private void enterEditMode()
    {
        rightPanel.setBorder(new TitledBorder("Edycja pracownika"));
        editMode = true;
        okButton.setText("Zatwierdź zmiany");
        undoButton.setVisible(true);
        nameField.setEnabled(true);
        emailField.setEnabled(true);
        phoneField.setEnabled(true);
        nameField.requestFocus();
    }
    private void exitEditMode()
    {
        editMode = false;
        rightPanel.setBorder(new TitledBorder("Dane pracownika"));
        okButton.setText("Edytuj");
        undoButton.setVisible(false);
        nameField.setEnabled(false);
        emailField.setEnabled(false);
        phoneField.setEnabled(false);
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
            workers.add(new Worker("Podaj imie i nazwisko"));
            listModel.addElement(workers.get(workers.size()-1).toString());
            workersList.ensureIndexIsVisible(workers.size()-1);
            workersList.setSelectedIndex(workers.size()-1);
            this.enterEditMode();
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
            if(!editMode)
            {
                this.enterEditMode();
            }
            else
            {
                int selected = workersList.getSelectedIndex();
                workers.get(selected).setNameSurname(nameField.getText());
                workers.get(selected).setEmail(emailField.getText());
                workers.get(selected).setPhoneNumber(phoneField.getText());
                listModel.set(selected, workers.get(selected).toString());
                JOptionPane.showMessageDialog(this,"Zmiany zatwierdzono pomyślnie.", "", JOptionPane.INFORMATION_MESSAGE);
                this.exitEditMode();
            }
        }
        else if(source == copyItem)
        {
            JTextField temp = copyFrom;
            String copied = temp.getText();
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Clipboard clipboard = toolkit.getSystemClipboard();
            StringSelection strSel = new StringSelection(copied);
            clipboard.setContents(strSel, null);
            
        }
        else if(source == pasteItem)
        {
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Clipboard clipboard = toolkit.getSystemClipboard();
            try {
                String pasted = (String) clipboard.getData(DataFlavor.stringFlavor);
                pasteTo.setText(pasted);
                pasteTo.requestFocus();
            } catch (UnsupportedFlavorException | IOException ex) {
                Logger.getLogger(WorkersWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        else if(source == undoButton)
        {
            
            this.exitEditMode();
            nameField.setText(workers.get(workersList.getSelectedIndex()).toString());
            emailField.setText(workers.get(workersList.getSelectedIndex()).getEmail());
            phoneField.setText(workers.get(workersList.getSelectedIndex()).getPhoneNumber());
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
        if(nameField.getText().equals(""))
            okButton.setEnabled(false);
    }

    @Override
    public void changedUpdate(DocumentEvent de) {
        
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        maybeShowPopup(me);
    }

    @Override
    public void mousePressed(MouseEvent me) {
        maybeShowPopup(me);
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        maybeShowPopup(me);
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        
    }

    @Override
    public void mouseExited(MouseEvent me) {
        
    }
    private void maybeShowPopup(MouseEvent e) {
        if (e.isPopupTrigger()) {
            
            copyFrom = (JTextField) e.getSource();
            
            if(!editMode)
                pasteItem.setEnabled(false);
            else
            {
                if(copyFrom.getSelectedText() != null)
                {
                    copyFrom = new JTextField(copyFrom.getSelectedText());
                }
                pasteTo = (JTextField) e.getSource();
                pasteItem.setEnabled(true);
            }
            popup.show(e.getComponent(), e.getX(), e.getY());
            
        }
    }
    
    
}
