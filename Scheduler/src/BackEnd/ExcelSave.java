
package BackEnd;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import javax.swing.JTabbedPane;
import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.*;
import jxl.write.Number;


public class ExcelSave {


    protected int X;
    protected int Y;
    

    public ExcelSave( String name) throws IOException, WriteException {
            String inputFile = (name+".xls");
            File file=new File(inputFile);
            WritableWorkbook myWbook = null;
            WorkbookSettings wbSettings = new WorkbookSettings();
            wbSettings.setLocale(new Locale("en", "EN"));
            myWbook = Workbook.createWorkbook(file, wbSettings);
           
            // create an Excel sheet

            myWbook.createSheet("Wyniki", 0);
            
            WritableSheet excelSheet=myWbook.getSheet(0);
            System.out.println( myWbook.getSheet(0).getName());
            // add something into the Excel sheet
           JTabbedPane panel;
           Label label;
           Number number;
           WritableCellFormat cFormat = new WritableCellFormat();
            WritableFont font = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
            cFormat.setFont(font);

           int j=0;
           int l=0;
           label = new Label(1,0,"Powierzchnia całkowita [ha]",cFormat);
           excelSheet.addCell(label);
           label = new Label(2,0,"Przepływ obliczeniowy [l/s]",cFormat);
           excelSheet.addCell(label);
           label = new Label(3,0,"Dopływy",cFormat);
           excelSheet.addCell(label);
           label = new Label(4,0,"Miarodajny przepływ deszczu [l/s]",cFormat);
           excelSheet.addCell(label);
//           number=new Number(4,1,this.zawartosc.q);
//                   excelSheet.addCell(number);
//           for(int i=0; i<=zawartosc.indeksDoplywow;i++){
//              panel=(JTabbedPane) zawartosc.Cards2.getComponent(i);
//              System.out.println(zawartosc.Sections.elementAt(i));
//              label = new Label(0,i+l+1,zawartosc.Sections.elementAt(i).toString(), cFormat);
//              excelSheet.addCell(label);
//              for(j=0; j<panel.getTabCount();j++){
//                    
//                   System.out.println(panel.getComponent(j).getName()+" "+j);
//                   label = new Label(0,l+j+i+2,panel.getComponent(j).getName());
//                   excelSheet.addCell(label);
//                   CustomRainCard card=(CustomRainCard) panel.getComponent(j);
//                   number=new Number(1,l+j+i+2,card.getAmax());
//                   excelSheet.addCell(number);
//                   number=new Number(2,l+j+i+2,card.Q);
//                   excelSheet.addCell(number);
//                   label = new Label(3,l+j+i+2,card.Inlets);
//                   excelSheet.addCell(label);
//               }
//              l=l+j+1;
//           }
           for (int i=0; i<5;i++){
           CellView cell=excelSheet.getColumnView(i);
           cell.setAutosize(true);
           excelSheet.setColumnView(i, cell);
           }
           myWbook.write();
           myWbook.close();
    }
}
