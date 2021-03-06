/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BackEnd;

import java.awt.Color;
import java.awt.Point;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Hardkor
 */
public class Work implements Serializable{
    private String workDescription;
    private Color color;
    private List<Point> cells;
    private Time startTime;
    private Time endTime;
    private Customer customer;
    private Date date;
    private String dogName;
    private String dogRace;
    private boolean payedByCard;
    private String price;
    //private Date 
    
    public Work (Date date,List <Point> cells,Time startTime, Time endTime, String description, Color color)
    {
        this.cells = cells;
        this.workDescription = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.color = color;
        this.date = date;
    }
    public Work (Date date,List <Point> cells,Time startTime, Time endTime, String description, Color color, String dogName, String dogRace, boolean payedByCard, String price)
    {
        this.cells = cells;
        this.workDescription = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.color = color;
        this.date = date;
        this.dogName = dogName;
        this.dogRace = dogRace;
        this.payedByCard = payedByCard;
        this.price = price;
    }
    public Date getDate()
    {
        return this.date;
    }
    public void setCustomer(Customer customer)
    {
        this.customer = customer;
        this.customer.addWork(this);
    }
    public Customer getCustomer()
    {
        return this.customer;
    }
    public void setColor(Color color)
    {
        this.color = color;
    }
    public String getWorkDescription()
    {
        return this.workDescription;
    }
    public List getCells()
    {
        return this.cells;
    }
    public Time getStartTime()
    {
        return this.startTime;
    }
    public Time getEndTime()
    {
        return this.endTime;
    }
    public Color getColor()
    {
        return this.color;
    }
    
    public String getDogName() {
        return dogName;
    }

    public String getDogRace() {
        return dogRace;
    }
    
    public boolean isPayedByCard() {
        return payedByCard;
    }

    public String getPrice() {
        return price;
    }
    
}
