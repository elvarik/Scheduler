/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BackEnd;

import jxl.CellType;
import jxl.Workbook;
import jxl.write.*;
import jxl.write.Number;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import jxl.Cell;
import jxl.CellView;

public class ExcelExport {
    private List<Worker> pracownicy;
    private static String EXCEL_FILE_LOCATION = "";
    public ExcelExport(String name,List<Worker> workers, JFrame parent) throws IOException, WriteException,FileNotFoundException{
        this.pracownicy=workers;
        this.EXCEL_FILE_LOCATION=(".\\"+name+".xls");
        WritableWorkbook myFirstWbook = null;
        JFileChooser fileChooser = new JFileChooser(){
        @Override
        public void approveSelection(){
            File f = getSelectedFile();
            if(f.exists()){
                int result = JOptionPane.showConfirmDialog(this,"Ten plik już istnieje, czy chcesz go podmienić?","Istniejący plik",JOptionPane.YES_NO_CANCEL_OPTION);
                switch(result){
                    case JOptionPane.YES_OPTION:
                        super.approveSelection();
                        return;
                    case JOptionPane.NO_OPTION:
                        return;
                    case JOptionPane.CLOSED_OPTION:
                        return;
                    case JOptionPane.CANCEL_OPTION:
                        cancelSelection();
                        return;
                }
            }
        super.approveSelection();
    }        
};
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel files", "xls","xlsx");
        fileChooser.setFileFilter(filter);
        int returnVal = fileChooser.showDialog(parent, "Zapisz jako");
        
        if(returnVal == JFileChooser.APPROVE_OPTION)
        {
            File file = fileChooser.getSelectedFile();
            System.out.printf(file.getAbsolutePath());
            String noExt = file.getAbsolutePath().split("\\.")[0];
            System.out.printf(noExt+".xls");
        try{
        myFirstWbook = Workbook.createWorkbook(new File(noExt+".xls"));
       } catch(FileNotFoundException | NullPointerException e){
           System.out.printf("Nie udało się utworzyć, może plik jest akurat otwarty");
           JOptionPane.showMessageDialog(null, 
                              "Nie udało się utworzyć, możliwe, że plik jest akurat otwarty", 
                              "Błąd", 
                              JOptionPane.WARNING_MESSAGE);
       }
            // create an Excel sheet
            int rows=0;
            int cols=0;
            int rowTemp=0;
            int colTemp=0;
            WritableFont cellFont = new WritableFont(WritableFont.ARIAL, 10);
            cellFont.setBoldStyle(WritableFont.BOLD);
    
            WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
            for(Worker pracownik : pracownicy){
                rows=0;
                cols=0;
                
                WritableSheet excelSheet = myFirstWbook.createSheet(pracownik.getNameSurname(), 0);
                Label email=new Label(cols,rows,"E-mail",cellFormat);
                excelSheet.addCell(email);
                cols++;
                email = new Label(cols, rows, pracownik.getEmail());
                excelSheet.addCell(email);

                cols=0;
                rows++;
                Label phoneNo = new Label(cols, rows, "Numer telefonu",cellFormat);
                excelSheet.addCell(phoneNo);
                cols++;
                phoneNo = new Label(cols, rows, pracownik.getPhoneNumber());
                excelSheet.addCell(phoneNo);
                cols=0;
                rows++;
                Label title = new Label(cols, rows, "Prace",cellFormat);
                excelSheet.addCell(title);
                rows++;
                rowTemp=rows;
                title = new Label(cols, rows, "Data",cellFormat);
                excelSheet.addCell(title);
                cols++;
                title = new Label(cols, rows, "Imię psa",cellFormat);
                excelSheet.addCell(title);
                cols++;
                title = new Label(cols, rows, "Rasa psa",cellFormat);
                excelSheet.addCell(title);
                cols++;
                title = new Label(cols, rows, "Imię klienta",cellFormat);
                excelSheet.addCell(title);
                cols++;
                title = new Label(cols, rows, "Numer telefonu klienta",cellFormat);
                excelSheet.addCell(title);
                cols++;
                title = new Label(cols, rows, "Start zlecenia",cellFormat);
                excelSheet.addCell(title);
                cols++;
                title = new Label(cols, rows, "Koniec zlecenia",cellFormat);
                excelSheet.addCell(title);
                cols++;
                title = new Label(cols, rows, "Uwagi",cellFormat);
                excelSheet.addCell(title);
                cols++;
                cols=0;
                rows++;
                Date [] dates = pracownik.getWorksDates();
                List <Date> datesList = new ArrayList<>(Arrays.asList(dates));
                datesList.sort((date1,date2)-> date1.compareTo(date2));
                Date prev=null;
//                for(Date date : dates){
//                    if (prev!= null) {
//                        prev.compareTo(date);
//                    }
//                    prev=date;
//                }
                for(Date date : datesList){
                    rowTemp=rows;
                    Label dataLabel=new Label(cols,rows,date.toString());
                    excelSheet.addCell(dataLabel);
                    List<Work> worklist=pracownik.getWorks(date);
                    colTemp=cols;
                    for(Work praca : worklist){
                        cols++;
                        dataLabel=new Label(cols,rows,praca.getDogName());
                        excelSheet.addCell(dataLabel);
                        cols++;
                        dataLabel=new Label(cols,rows,praca.getDogRace());
                        excelSheet.addCell(dataLabel);
                        cols++;
                        dataLabel=new Label(cols,rows,praca.getCustomer().getName());
                        excelSheet.addCell(dataLabel);
                        cols++;
                        dataLabel=new Label(cols,rows,praca.getCustomer().getPhoneNumber());
                        excelSheet.addCell(dataLabel);
                        cols++;
                        dataLabel=new Label(cols,rows,praca.getStartTime().toString());
                        excelSheet.addCell(dataLabel);
                        cols++;
                        dataLabel=new Label(cols,rows,praca.getEndTime().toString());
                        excelSheet.addCell(dataLabel);
                        cols++;
                        dataLabel=new Label(cols,rows,praca.getWorkDescription());
                        excelSheet.addCell(dataLabel);
                        cols=0;
                        rows++;
                    }
                    cols=0;
                }
                
                cols++;

                sheetAutoFitColumns(excelSheet);
            }
            myFirstWbook.write();
            
            
            if (myFirstWbook != null) {
                try {
                    myFirstWbook.close();
                    JOptionPane.showMessageDialog(null, 
                              "Plik " + file.getName() + " został utworzony", 
                              "Utworzono", 
                              JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (WriteException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private void sheetAutoFitColumns(WritableSheet sheet) {
    for (int i = 0; i < sheet.getColumns(); i++) {
        Cell[] cells = sheet.getColumn(i);
        int longestStrLen = -1;

        if (cells.length == 0)
            continue;

        /* Find the widest cell in the column. */
        for (int j = 0; j < cells.length; j++) {
            if ( cells[j].getContents().length() > longestStrLen ) {
                String str = cells[j].getContents();
                if (str == null || str.isEmpty())
                    continue;
                longestStrLen = str.trim().length();
            }
        }

        /* If not found, skip the column. */
        if (longestStrLen == -1) 
            continue;

        /* If wider than the max width, crop width */
        if (longestStrLen > 255)
            longestStrLen = 255;

        CellView cv = sheet.getColumnView(i);
        cv.setSize(longestStrLen * 256 + 100); /* Every character is 256 units wide, so scale it. */
        sheet.setColumnView(i, cv);
    }
}
}
