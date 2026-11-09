package uy.edu.ort.battleshipdemo.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

public class Boat {

    @Getter
    private BoatType type;
    private List<Cell> cells = new ArrayList<>();

    public Boat(BoatType type) {
        this.type = type;
    }

    public int getSize() {
        return type.getSize();
    }

    public void addCell(Cell cell) {
        cells.add(cell);
    }

    public void clearCells() {
        cells.clear();
    }

    public boolean isPlaced() {
        return !cells.isEmpty();
    }

    public boolean isSunk() {
        return isPlaced() && getHits() == getSize();
    }

    public int getHealthyParts() {
        return getSize() - getHits();
    }

    private int getHits() {
        int hits = 0;
        for (Cell cell : cells) {
            if (cell.isShot()) {
                hits++;
            }
        }
        return hits;
    }
}
