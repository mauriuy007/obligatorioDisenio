package uy.edu.ort.battleshipdemo.model;

import lombok.Getter;

// row es la coordenada vertical y column la horizontal; (0, 0) es la celda inferior izquierda.
public class Cell {

    @Getter
    private int row;
    @Getter
    private int column;
    @Getter
    private Boat boat;
    @Getter
    private boolean shot;

    public Cell(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public void setBoat(Boat boat) {
        this.boat = boat;
    }

    public boolean hasBoat() {
        return boat != null;
    }

    public void shoot() {
        this.shot = true;
    }

    public boolean isHit() {
        return shot && hasBoat();
    }

    public boolean isOpenHit() {
        return isHit() && !boat.isSunk();
    }
}
