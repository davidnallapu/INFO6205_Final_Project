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
public class Grid {

    private int boundaryX;
    private int boundaryY;

    public Grid(int sizeX, int sizeY){
        boundaryX = sizeX;
        boundaryY = sizeY;
    }

    public int getBoundaryX() {
        return boundaryX;
    }

    public void setBoundaryX(int boundaryX) {
        this.boundaryX = boundaryX;
    }

    public int getBoundaryY() {
        return boundaryY;
    }

    public void setBoundaryY(int boundaryY) {
        this.boundaryY = boundaryY;
    }

    
}
