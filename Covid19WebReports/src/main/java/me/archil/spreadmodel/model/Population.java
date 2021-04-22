/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.archil.spreadmodel.model;

import java.util.ArrayList;

/**
 *
 * @author Achiko
 */
public class Population {

    ArrayList<Person> persons;

    public Population() {
        persons = new ArrayList<Person>();
    }
    
    public Population(int numberOfPeople) {
        persons = new ArrayList<Person>();
        for (int i=0;i<numberOfPeople;i++){
            persons.add(new Person());
        }
    }

    public ArrayList<Person> getPersons() {
        return persons;
    }

    public void setPersons(ArrayList<Person> persons) {
        this.persons = persons;
    }
    
    
}
