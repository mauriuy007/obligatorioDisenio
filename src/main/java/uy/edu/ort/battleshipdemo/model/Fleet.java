package uy.edu.ort.battleshipdemo.model;

import java.util.ArrayList;
import java.util.List;

public class Fleet {

    private List<Boat> boats = new ArrayList<>();

    public List<Boat> getBoats() {
        return boats;
    }

    public void setBoats(List<Boat> boats) {
        this.boats = boats;
    }
}
