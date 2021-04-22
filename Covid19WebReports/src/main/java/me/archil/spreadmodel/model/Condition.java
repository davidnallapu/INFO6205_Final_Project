/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.archil.spreadmodel.model;

/**
 *
 * @author Achiko
 */
public class Condition {
    
    public enum conditionOptions {
         susceptible,
         infected,
         immune
    }
    
    private conditionOptions condition;
    
    public Condition(){
        condition = conditionOptions.susceptible;
    }

    public conditionOptions getCondition() {
        return condition;
    }

    public void setCondition(conditionOptions condition) {
        this.condition = condition;
    }
    
    
}
