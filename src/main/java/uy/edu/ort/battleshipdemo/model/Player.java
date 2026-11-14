package uy.edu.ort.battleshipdemo.model;

import java.util.ArrayList;
import java.util.List;

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

    public void placeNextBoat(int row, int column, boolean horizontal) throws BattleshipException {
        ocean.placeBoat(fleet.getNextBoatToPlace(), row, column, horizontal);
    }

    public void deployRandomly() {
        ocean.placeRandomly(fleet);
    }

    public List<Boat> getUnplacedBoats() {
        return fleet.getUnplacedBoats();
    }

    public int getHealthyParts() {
        return fleet.getHealthyParts();
    }

    public int getFleetSize() {
        return fleet.getTotalSize();
    }

    public boolean isDeployed() {
        return fleet.isDeployed();
    }

    public void receiveShot(int row, int column) throws BattleshipException {
        Cell cell = ocean.shoot(row, column);
        if (cell.hasBoat() && cell.getBoat().isSunk()) {
            sunkBoats.add(cell.getBoat());
        }
    }

    public boolean isDefeated() {
        return fleet.isSunk();
    }
}
