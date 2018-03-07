/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BackEnd;
import java.io.Serializable;
import java.util.Comparator;
/**
 *
 * @author Hardkor
 */
public class Time implements Comparator<Time>, Comparable<Time>, Serializable{
    
    private int hour, minute;
    public Time(String timeString)
    {
        int pointPosition = timeString.indexOf(":");
        String hourString = timeString.substring(0,pointPosition);
        String minuteString = timeString.substring(pointPosition+1, timeString.length());

        this.hour = Integer.parseInt(hourString);
        this.minute = Integer.parseInt(minuteString);
        
    }
    public Time(int hour, int minute)
    {
        this.hour = hour;
        this.minute = minute;
    }

    @Override
    public String toString() {
        return hour + ":" + minute;
    }

    @Override
    public int compare(Time t, Time t1) {
        if(t.hour == t1.hour)
        {
            return t.minute - t1.minute;
        }
        return t.hour - t1.hour;
    }

    @Override
    public int compareTo(Time t) {
        if(this.hour == t.hour)
        {
            return this.minute - t.minute;
        }
        return this.hour - t.hour;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + this.hour;
        hash = 17 * hash + this.minute;
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
        final Time other = (Time) obj;
        if (this.hour != other.hour) {
            return false;
        }
        if (this.minute != other.minute) {
            return false;
        }
        return true;
    }
    
}

