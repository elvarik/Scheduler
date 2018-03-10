/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BackEnd;

import java.io.Serializable;


/**
 *
 * @author Hardkor
 */
public class Customer implements Serializable{
    private String name;
    private String dogName;
    private String dogRace;
    private String phoneNumber;
    private boolean payedByCard;
    private String price;
    public Customer(String name,String dogName, String dogRace, String phoneNumber,String price, boolean payedByCard)
    {
        this.name = name;
        this.dogName= dogName;
        this.dogRace = dogRace;
        this.phoneNumber = phoneNumber;
        this.payedByCard = payedByCard;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getDogName() {
        return dogName;
    }

    public String getDogRace() {
        return dogRace;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public boolean isPayedByCard() {
        return payedByCard;
    }

    public String getPrice() {
        return price;
    }
    
    
    
}
