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
public class Worker {
    private String name, surname;
    private WorkDay;
    public Worker(String name, String surname)
    {
        this.name = name;
        this.surname = surname;
    }

    @Override
    public String toString() {
        return name + " " + surname;
    }
    
}
