package uy.edu.ort.battleshipdemo.model;

import java.util.ArrayList;
import java.util.List;

import uy.edu.ort.battleshipdemo.exception.FleetException;

public class Fleet {

    public static final int MIN_BOATS_PER_TYPE = 0;
    public static final int MAX_BOATS_PER_TYPE = 6;

    private List<Boat> boats = new ArrayList<>();

    public List<Boat> getBoats() {
        return boats;
    }

    public void setBoats(List<Boat> boats) {
        this.boats = boats;
    }

    public void addBoats(BoatType type, int quantity) throws FleetException {
        if (quantity < MIN_BOATS_PER_TYPE || quantity > MAX_BOATS_PER_TYPE) {
            throw FleetException.invalidQuantity(type.getName(), MIN_BOATS_PER_TYPE, MAX_BOATS_PER_TYPE);
        }
        for (int i = 0; i < quantity; i++) {
            Boat boat = new Boat();
            boat.setType(type);
            boats.add(boat);
        }
    }

    public void validate() throws FleetException {
        if (boats.isEmpty()) {
            throw FleetException.empty();
        }
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
