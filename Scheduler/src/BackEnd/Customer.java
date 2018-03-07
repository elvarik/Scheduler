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
    public Customer(String name,String dogName, String dogRace, String phoneNumber)
    {
        this.name = name;
        this.dogName= dogName;
        this.dogRace = dogRace;
        this.phoneNumber = phoneNumber;
    }
    
    
}
