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
    private Map<DateTime, Work> works;
    private String eMail;
    private String phoneNumber;
    public Worker(String nameSurname)
    {
        this.nameSurname = nameSurname;
        works = new TreeMap<>();
        eMail = "Pusty";
        phoneNumber = "Pusty";
    }
    /** Puts Work Day on specified date on a Worker list. List is sorted by date automatically*/
    public void putWork(DateTime dateTime, Work work)
    {
        works.put(dateTime, work);
    }
    /** Gets Work of specified date on a Worker list. List is sorted by date automatically*/
    public Work getWork(Date date)
    {
        return works.get(date);
    }
    /** Gets array of all dates on a worker's list*/
    public DateTime[] getWorksDates()
    {
        Set<DateTime> workDates = works.keySet();
        return workDates.toArray(new DateTime[workDates.size()]);
    }
    public Map<DateTime,Work> getWorksInMonth(int month, int year)
    {
        Map<DateTime,Work> tmpWorks = new TreeMap<>();
        DateTime [] dates = this.getWorksDates();
        for(int i = 0; i < dates.length; i++)
        {
            if(dates[i].getMonth() == month && dates[i].getYear() == year)
            {
                tmpWorks.put(dates[i], works.get(dates[i]));
            }
        }
        return tmpWorks;
    }
    public void setNameSurname(String nameSurname)
    {
        this.nameSurname = nameSurname;
    }
    public void setEmail(String eMail)
    {
        this.eMail = eMail;
    }
    public String getEmail()
    {
        return this.eMail;
    }
    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }
    public String getPhoneNumber()
    {
        return this.phoneNumber;
    }
    @Override
    public String toString() {
        return nameSurname;
    }
    
}
