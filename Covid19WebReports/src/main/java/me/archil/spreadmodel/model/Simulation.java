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
public class Simulation {
    
    private int timeIndex = 0;
    
    private Grid grid;
    private Population people;
    
    public Simulation(int numberOfPeople, int gridSizeX, int gridSizeY){
        grid = new Grid(gridSizeX, gridSizeY);
        people = new Population(numberOfPeople);
        
        for (Person p : people.getPersons()){
            p.setLocation(new Location(grid));
        }
    }
    
    public void play(int steps){
        
        for (int i=0;i<steps;i++){
            step();
        }
        
    }
    
    public void step(){
       for (Person p : people.getPersons()){
            p.moveRandomly();
       }
       timeIndex ++;
    }
    
    
    public String toString(){
        return Integer.toString(people.getPersons().size());
    }

    public int getTimeIndex() {
        return timeIndex;
    }

    public void setTimeIndex(int timeIndex) {
        this.timeIndex = timeIndex;
    }

    public Grid getGrid() {
        return grid;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public Population getPeople() {
        return people;
    }

    public void setPeople(Population people) {
        this.people = people;
    }
    
    
}
