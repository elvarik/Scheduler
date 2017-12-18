/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BackEnd;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
/**
 *
 * @author Hardkor
 */
public class Worker {
    private String nameSurname;
    private Map<Date, WorkDay> workDays;
    public Worker(String nameSurname)
    {
        this.nameSurname = nameSurname;
        workDays = new TreeMap<>();
    }
    /** Puts Work Day on specified date on a Worker list. List is sorted by date automatically*/
    public void putWorkDay(Date date, WorkDay workDay)
    {
        workDays.put(date, workDay);
    }
    /** Gets Work Day of specified date on a Worker list. List is sorted by date automatically*/
    public WorkDay getWorkDay(Date date)
    {
        return workDays.get(date);
    }
    /** Gets array of all dates on a worker's list*/
    public Date[] getWorkDates()
    {
        Set<Date> workDates = workDays.keySet();
        return workDates.toArray(new Date[workDates.size()]);
    }
    @Override
    public String toString() {
        return nameSurname;
    }
    
}
