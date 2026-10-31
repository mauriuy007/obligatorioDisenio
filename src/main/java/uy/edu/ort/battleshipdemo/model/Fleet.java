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

    public int getTotalSize() {
        int total = 0;
        for (Boat boat : boats) {
            total += boat.getType().getSize();
        }
        return total;
    }

    // TODO: descontar las partes impactadas cuando existan los impactos
    public int getHealthyParts() {
        return getTotalSize();
    }
}
