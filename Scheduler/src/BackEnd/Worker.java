/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BackEnd;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
/**
 *
 * @author Hardkor
 */
public class Worker implements Serializable{
    private String nameSurname;
    private Map<Date, List<Work>> works;
    private String eMail;
    private String phoneNumber;
    public Worker(String nameSurname)
    {
        this.nameSurname = nameSurname;
        works = new TreeMap<>();
        eMail = "Pusty";
        phoneNumber = "Pusty";
    }
    /** Puts Work on specified date on a Worker list. List is sorted by date automatically*/
    public void putWork(Date date, Work work)
    {
        List <Work> tmpArray;
        if(works.get(date)!= null)
            tmpArray = works.get(date);
        else
            tmpArray = new ArrayList<>();
        
        tmpArray.add(work);
        works.put(date, tmpArray);
        
    }
    /** Gets Works of specified date on a Worker list. List is sorted by date automatically*/
    public List<Work> getWorks(Date date)
    {
        return works.get(date);
    }
    /** Gets array of all dates on a worker's list*/
    public Date[] getWorksDates()
    {
        Set<Date> workDates = works.keySet();
        return workDates.toArray(new Date[workDates.size()]);
    }
    public Map<Date, List<Work>> getWorksInMonth(int month, int year)
    {
        Map<Date,List<Work>> tmpWorks = new TreeMap<>();
        Date [] dates = this.getWorksDates();
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

    public String getNameSurname() {
        return nameSurname;
    }

    public Map<Date, List<Work>> getWorks() {
        return works;
    }

    public String geteMail() {
        return eMail;
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
