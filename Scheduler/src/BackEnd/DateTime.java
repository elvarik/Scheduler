/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BackEnd;

import java.util.Comparator;
import java.util.Objects;

/**
 *
 * @author Hardkor
 */
public class DateTime implements Comparator<DateTime>, Comparable<DateTime> {
    private Date date;
    private Time time;
    public DateTime(Date date, Time time)
    {
        this.date = date;
        this.time = time;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.date);
        hash = 41 * hash + Objects.hashCode(this.time);
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
        final DateTime other = (DateTime) obj;
        if (this.date.getDay() != other.date.getDay()) {
            return false;
        }
        if (this.date.getMonth()!= other.date.getMonth()) {
            return false;
        }
        if (this.date.getYear() != other.date.getYear()) {
            return false;
        }
        if (!Objects.equals(this.time, other.time)) {
            return false;
        }
        return true;
    }
    public int getDay()
    {
        return this.date.getDay();
    }
    public int getMonth()
    {
        return this.date.getMonth();
    }
    public int getYear()
    {
        return this.date.getYear();
    }
    public Time getTime()
    {
        return this.time;
    }
    public Date getDate()
    {
        return this.date;
    }
    @Override
    public int compare(DateTime t, DateTime t1) {
        if(t.date.equals(t1.date))
            return t.time.compareTo(t1.time);
        else
            return t.date.compareTo(t1.date);
    }

    @Override
    public int compareTo(DateTime t) {
        if(this.date.equals(t.date))
            return this.time.compareTo(t.time);
        else
            return this.date.compareTo(t.date);
    }
}
