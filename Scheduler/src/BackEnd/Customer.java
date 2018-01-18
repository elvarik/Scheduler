/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BackEnd;

import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Hardkor
 */
public class Customer {
    private String dogName;
    private String dogRace;
    private String phoneNumber;
    private Map<DateTime, Appointment> appointments;
    public Customer(String dogName, String dogRace, String phoneNumber)
    {
        this.dogName= dogName;
        this.dogRace = dogRace;
        this.phoneNumber = phoneNumber;
        this.appointments = new TreeMap<>();
    }
    public void putAppointment(DateTime date, Appointment appointment)
    {
        this.appointments.put(date, appointment);
    }
    public Map<DateTime, Appointment> getAppointments()
    {
        return this.appointments;
    }
    public Appointment getAppointment(DateTime date)
    {
        return this.appointments.get(date);
    }
    
}
