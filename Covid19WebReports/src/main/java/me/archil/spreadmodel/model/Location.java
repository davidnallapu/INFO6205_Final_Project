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
public class Location {

    private Grid grid;
    private int x;
    private int y;


    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }



    public Location(int x, int y, Grid grid) {
        this.x = x;
        this.y = y;
        this.grid = grid;
    }

    public Location(Grid grid) {
        this.grid = grid;
        Random r = new Random();
        this.x = r.nextInt(grid.getBoundaryX());
        this.y = r.nextInt(grid.getBoundaryY());
    }

    public void moveRandomly() {
        Random r = new Random();
        int dx = r.nextInt(2) + 1;
        if (dx == 2) {
            dx = -1;
        }
        int dy = r.nextInt(2) + 1;
        if (dy == 2) {
            dy = -1;
        }

        if (x + dx > grid.getBoundaryX()) {
            x -= dx;
        } else {
            x += dx;
        }

        if (y + dy > grid.getBoundaryY()) {
            y -= dy;
        } else {
            y += dy;
        }

    }
}
