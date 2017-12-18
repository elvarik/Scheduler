/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BackEnd;
import java.util.Calendar;
import java.time.YearMonth;
/**
 *
 * @author Hardkor
 */
public final class CalendarPlotter {
   final public String[] Months={"Styczeń","Luty","Marzec","Kwiecień","Maj","Czerwiec","Lipiec",
        "Sierpień","Wrzesień","Październik","Listopad","Grudzień"};
    private Calendar cal;
    YearMonth yearMonthObj;
    public CalendarPlotter()
    {
        cal = Calendar.getInstance();
    }
    /** Gets current day number*/
    public int getCurrentDay()
    {
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return day;
    }
    /** Gets current month number*/
    public int getCurrentMonth()
    {
        int month = cal.get(Calendar.MONTH)+1;
        return month;
    }
    /** Gets current year number*/
    public int getCurrentYear()
    {
        int year = cal.get(Calendar.YEAR);
        return year;
    }
    /** Gets amount of days in specified month*/
    public int getAmountOfDays(int month){ 
        
        if(month ==0){
            return -1;
        }
        int year = cal.get(Calendar.YEAR);
        yearMonthObj = YearMonth.of(year, month);
        return 0;
    }
    /** Gets amount of days in specified month and year*/
    public int getAmountOfDays(int month, int year){ 
        
        if(month ==0){
            return -1;
        }
        yearMonthObj = YearMonth.of(year, month);
        return yearMonthObj.lengthOfMonth();
    }
    /** Gets amount of days in current month*/
    public int getAmountOfDays(){ 
        
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        yearMonthObj = YearMonth.of(year, month);
        
        return yearMonthObj.lengthOfMonth();
    }
    
}
