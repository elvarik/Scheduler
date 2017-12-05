/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BackEnd;
import java.util.Map;
import java.util.Collection;
import java.util.TreeMap;
/**
 *
 * @author Hardkor
 */
public class Worker {
    private String name, surname;
    private Map<Date, WorkDay> workDays;
    public Worker(String name, String surname)
    {
        this.name = name;
        this.surname = surname;
        workDays = new TreeMap<>();
    }
    /** Puts Work Day on specified date on a Worker list. List is sorted by date automatically*/
    public void putWorkDay(Date date, WorkDay workDay)
    {
        workDays.put(date, workDay);
    }
    @Override
    public String toString() {
        return name + " " + surname;
    }
    
}
