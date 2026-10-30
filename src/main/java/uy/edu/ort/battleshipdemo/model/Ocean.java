package uy.edu.ort.battleshipdemo.model;

import java.util.ArrayList;
import java.util.List;

public class Ocean {

    private List<Cell> cells = new ArrayList<>();

    public List<Cell> getCells() {
        return cells;
    }

    public void setCells(List<Cell> cells) {
        this.cells = cells;
    }
}
