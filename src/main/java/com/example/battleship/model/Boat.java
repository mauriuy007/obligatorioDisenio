package com.example.battleship.model;

import java.util.ArrayList;
import java.util.List;

public class Boat {

    private boolean sunk;
    private BoatType type;
    private List<Cell> cells = new ArrayList<>();

    public boolean isSunk() {
        return sunk;
    }

    public void setSunk(boolean sunk) {
        this.sunk = sunk;
    }

    public BoatType getType() {
        return type;
    }

    public void setType(BoatType type) {
        this.type = type;
    }

    public List<Cell> getCells() {
        return cells;
    }

    public void setCells(List<Cell> cells) {
        this.cells = cells;
    }
}
