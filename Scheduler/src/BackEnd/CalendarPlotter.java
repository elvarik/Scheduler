/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BackEnd;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.time.YearMonth;
import java.util.GregorianCalendar;
/**
 *
 * @author Hardkor
 */
public final class CalendarPlotter {
   final public String[] Months={"Styczeń","Luty","Marzec","Kwiecień","Maj","Czerwiec","Lipiec",
        "Sierpień","Wrzesień","Październik","Listopad","Grudzień"};
   final public String[] Days={"poniedziałek","wtorek","środa","czwartek","piątek","sobota","niedziela"};
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
    public String getDayName(int day, int month, int year)
    {
        java.util.Date date1 = (new GregorianCalendar(year, month-1, day)).getTime();
        String dayName = new SimpleDateFormat("EEEE").format(date1);
        return dayName;
    }
    public String getDayName(Date date)
    {
        java.util.Date date1 = (new GregorianCalendar(date.getYear(), date.getMonth()-1, date.getDay())).getTime();
        String dayName = new SimpleDateFormat("EEEE").format(date1);
        return dayName;
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
        
        int year = cal.get(Calendar.YEAR);
        
        yearMonthObj = YearMonth.of(year, month);
        
        return yearMonthObj.lengthOfMonth();
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
