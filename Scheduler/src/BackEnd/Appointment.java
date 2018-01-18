/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BackEnd;

/**
 *
 * @author Hardkor
 */
public class Appointment {
    String appointmentDisc;
    Worker attendant;
    String place;
    String price;
    boolean payedByCard;
    
    public Appointment(String appointmentDisc, Worker attendant, String place, String price, boolean payedByCard )
    {
        this.appointmentDisc =appointmentDisc;
        this.attendant = attendant;
        this.place = place;
        this.price =price;
        this.payedByCard = payedByCard;
    }
}
