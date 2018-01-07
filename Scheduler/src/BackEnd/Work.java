/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BackEnd;

import java.awt.Color;
import java.awt.Point;
import java.util.List;

/**
 *
 * @author Hardkor
 */
public class Work {
    private String workDescription ;
    private Color color;
    private List<Point> cells;
    private Time startTime;
    private Time endTime;
    
    public Work (List <Point> cells,Time startTime, Time endTime, String description, Color color)
    {
        this.cells = cells;
        this.workDescription = description;
        this.startTime = startTime;
        this.endTime = endTime;
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
    
}
