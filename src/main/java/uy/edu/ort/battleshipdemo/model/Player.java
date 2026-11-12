package uy.edu.ort.battleshipdemo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lombok.Getter;

public class Player {

    @Getter
    private Ocean ocean;
    @Getter
    private Fleet fleet;
    @Getter
    private List<Boat> sunkBoats = new ArrayList<>();

    public Player(Fleet fleet, int width, int height) {
        this.fleet = fleet;
        this.ocean = new Ocean(width, height);
    }

    public void placeBoat(Boat boat, int row, int column, boolean horizontal) throws BattleshipException {
        ocean.placeBoat(boat, row, column, horizontal);
    }

    public void placeNextBoat(int row, int column, boolean horizontal) throws BattleshipException {
        placeBoat(fleet.getNextBoatToPlace(), row, column, horizontal);
    }

    public void deployRandomly(Random random) {
        ocean.placeRandomly(fleet, random);
    }

    public boolean isDeployed() {
        return fleet.isDeployed();
    }

    public Cell receiveShot(int row, int column) throws BattleshipException {
        Cell cell = ocean.shoot(row, column);
        if (cell.hasBoat() && cell.getBoat().isSunk()) {
            sunkBoats.add(cell.getBoat());
        }
        return cell;
    }

    public boolean isDefeated() {
        return fleet.isSunk();
    }
}
