package uy.edu.ort.battleshipdemo.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

public class Fleet {

    private static final int MIN_BOATS_PER_TYPE = 0;
    private static final int MAX_BOATS_PER_TYPE = 6;

    @Getter
    private List<Boat> boats = new ArrayList<>();

    public void addBoats(BoatType type, int quantity) throws BattleshipException {
        if (quantity < MIN_BOATS_PER_TYPE || quantity > MAX_BOATS_PER_TYPE) {
            throw new BattleshipException("La cantidad de " + type.getName() + " debe estar entre "
                    + MIN_BOATS_PER_TYPE + " y " + MAX_BOATS_PER_TYPE);
        }
        for (int i = 0; i < quantity; i++) {
            boats.add(new Boat(type));
        }
    }

    // Ordenados de mayor a menor tamaño.
    public List<Boat> getUnplacedBoats() {
        List<Boat> unplaced = new ArrayList<>();
        for (Boat boat : boats) {
            if (!boat.isPlaced()) {
                unplaced.add(positionBySize(unplaced, boat), boat);
            }
        }
        return unplaced;
    }

    private int positionBySize(List<Boat> sorted, Boat boat) {
        int position = 0;
        while (position < sorted.size() && sorted.get(position).getSize() >= boat.getSize()) {
            position++;
        }
        return position;
    }

    public Boat getNextBoatToPlace() throws BattleshipException {
        List<Boat> unplaced = getUnplacedBoats();
        if (unplaced.isEmpty()) {
            throw new BattleshipException("Ya posicionó todos sus barcos, puede librar la batalla");
        }
        return unplaced.get(0);
    }

    public void validate() throws BattleshipException {
        if (boats.isEmpty()) {
            throw new BattleshipException("La flota debe tener al menos un barco");
        }
    }

    public int getTotalSize() {
        int total = 0;
        for (Boat boat : boats) {
            total += boat.getType().getSize();
        }
        return total;
    }

    public int getHealthyParts() {
        int healthy = 0;
        for (Boat boat : boats) {
            healthy += boat.getHealthyParts();
        }
        return healthy;
    }

    public boolean isSunk() {
        for (Boat boat : boats) {
            if (!boat.isSunk()) {
                return false;
            }
        }
        return true;
    }

    public boolean isDeployed() {
        for (Boat boat : boats) {
            if (!boat.isPlaced()) {
                return false;
            }
        }
        return true;
    }
}
