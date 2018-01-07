/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BackEnd;

import java.util.Comparator;


/**
 *
 * @author Hardkor
 */
public class Date implements Comparator<Date>, Comparable<Date> {
    private int day, month, year;
    private final String[] months={"styczeń","luty","marzec","kwiecień","maj",
        "czerwiec","lipiec","sierpień","wrzesień","październik",
        "listopad","grudzień"};
    public Date()
    {
        CalendarPlotter cal = new CalendarPlotter();
        this.day = cal.getCurrentDay();
        this.month = cal.getCurrentMonth();
        this.year = cal.getCurrentYear();
    }
    public Date(int day,int month, int year){
        this.day = day;
        this.month = month;
        this.year = year;
    }
    public Date(int month, int year)
    {
        this.day = 1;
        this.month = month;
        this.year = year;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + this.day;
        hash = 53 * hash + this.month;
        hash = 53 * hash + this.year;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Date other = (Date) obj;
        if (this.day != other.day) {
            return false;
        }
        if (this.month != other.month) {
            return false;
        }
        if (this.year != other.year) {
            return false;
        }
        return true;
    }
    public String monthYearString()
    {
        return months[month-1]+ " " + year;
    }
    public int getDay()
    {
        return this.day;
    }
    public int getMonth()
    {
        return this.month;
    }
    public int getYear()
    {
        return this.year;
    }

    @Override
    public String toString() {
        return day + " " + months[month-1]+ " " + year;
    }

    @Override
    public int compare(Date t, Date t1) {
       if(t.year == t1.year)
       {
           if(t.month == t1.month)
           {
               return t.day - t1.day;
           }
           else
           {
               return t.month - t1.month;
           }
       }
       else
       {
           return t.year - t1.year;
       }
    }

    @Override
    public int compareTo(Date t) {
        if(this.year == t.year)
       {
           if(this.month == t.month)
           {
               return this.day - t.day;
           }
           else
           {
               return this.month - t.month;
           }
       }
       else
       {
           return this.year - t.year;
       }
    }
    
    
}
