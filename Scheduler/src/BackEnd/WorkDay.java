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
public class WorkDay {
    private Map<Time, String> day;
    public WorkDay()
    {
        day = new TreeMap<>();
    }
    /** Gets work description from day on specified time*/
    public String getWork(Time time)
    {
        return day.get(time);
    }
    /** Puts work description on list on specified time*/
    public void putInWorkDay(Time time, String work)
    {
        day.put(time, work);
    }
    /** Gets array of all times on a work day's list*/
    public Time[] getWorkTimes()
    {
        Set<Time> times = day.keySet();
        Time [] workTimes = times.toArray(new Time[times.size()]);
        
        return workTimes;
        
         
    }
}
