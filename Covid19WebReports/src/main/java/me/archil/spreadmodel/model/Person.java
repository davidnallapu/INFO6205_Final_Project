/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.archil.spreadmodel.model;

import java.util.Random;

/**
 *
 * @author Achiko
 */
public class Person {
    private Location location;
    private Condition condition;
    private int id;
    private static int counter = 0;
    
    public Person(){
        this.condition = new Condition();
        this.id = counter;
        counter ++;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public Location getLocation() {
        return location;
    }
    
    public void moveRandomly(){
        location.moveRandomly();        
    }

    public int getId() {
        return id;
    }
    
    
}
