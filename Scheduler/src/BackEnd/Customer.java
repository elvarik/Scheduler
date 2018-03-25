/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BackEnd;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 *
 * @author Hardkor
 */
public class Customer implements Serializable{
    private String name;
    private String phoneNumber;
    private List <Work> works;
    public Customer(String name, String phoneNumber)
    {
        this.name = name;
        this.phoneNumber = phoneNumber;
        works = new ArrayList<>();
    }

    public String getName() {
        return name;
    }
    public void addWork(Work work)
    {
        works.add(work);
    }
    public void removeWork(Work work)
    {
        works.remove(work);
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public List<Work> getWorks() {
        return works;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setWorks(List<Work> works) {
        this.works = works;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.name);
        hash = 29 * hash + Objects.hashCode(this.phoneNumber);
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
        final Customer other = (Customer) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.phoneNumber, other.phoneNumber)) {
            return false;
        }
        return true;
    }
    
    
}
