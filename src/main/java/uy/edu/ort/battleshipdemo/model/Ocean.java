package uy.edu.ort.battleshipdemo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lombok.Getter;

// Las celdas se guardan fila por fila: la celda (row, column) está en la posición row * width + column.
public class Ocean {

    @Getter
    private int width;
    @Getter
    private int height;
    @Getter
    private List<Cell> cells = new ArrayList<>();

    public Ocean(int width, int height) {
        this.width = width;
        this.height = height;
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                cells.add(new Cell(row, column));
            }
        }
    }

    public int getSize() {
        return width * height;
    }

    public boolean isInside(int row, int column) {
        return row >= 0 && row < height && column >= 0 && column < width;
    }

    public Cell getCell(int row, int column) {
        return cells.get(row * width + column);
    }

    public boolean isUnshot(int row, int column) {
        return isInside(row, column) && !getCell(row, column).isShot();
    }

    public boolean isOpenHit(int row, int column) {
        return isInside(row, column) && getCell(row, column).isOpenHit();
    }

    public List<Cell> getUnshotCells() {
        List<Cell> unshot = new ArrayList<>();
        for (Cell cell : cells) {
            if (!cell.isShot()) {
                unshot.add(cell);
            }
        }
        return unshot;
    }

    public List<Cell> getOpenHits() {
        List<Cell> hits = new ArrayList<>();
        for (Cell cell : cells) {
            if (cell.isOpenHit()) {
                hits.add(cell);
            }
        }
        return hits;
    }

    public Cell shoot(int row, int column) throws BattleshipException {
        if (!isInside(row, column)) {
            throw new BattleshipException("La celda no pertenece al océano");
        }
        Cell cell = getCell(row, column);
        if (cell.isShot()) {
            throw new BattleshipException("Ya se disparó a esa celda");
        }
        cell.shoot();
        return cell;
    }

    // La proa queda en (row, column): es el extremo izquierdo si es horizontal o el inferior si es vertical.
    public void placeBoat(Boat boat, int row, int column, boolean horizontal) throws BattleshipException {
        Position position = new Position(row, column, horizontal);
        validatePlacement(boat, position);
        place(boat, position);
    }

    private void validatePlacement(Boat boat, Position position) throws BattleshipException {
        if (!fits(boat, position)) {
            throw new BattleshipException("El barco queda fuera de los límites del océano");
        }
        if (overlaps(boat, position)) {
            throw new BattleshipException("El barco se superpone con otro barco ya ubicado");
        }
        if (touches(boat, position)) {
            throw new BattleshipException("El barco no puede tocar a otro barco, debe haber agua entre ellos");
        }
    }

    public void placeRandomly(Fleet fleet, Random random) {
        while (!tryPlaceRandomly(fleet, random)) {
            clear();
        }
    }

    private boolean tryPlaceRandomly(Fleet fleet, Random random) {
        for (Boat boat : fleet.getBoats()) {
            List<Position> options = validPositions(boat);
            if (options.isEmpty()) {
                return false;
            }
            place(boat, options.get(random.nextInt(options.size())));
        }
        return true;
    }

    private List<Position> validPositions(Boat boat) {
        List<Position> positions = new ArrayList<>();
        for (Cell cell : cells) {
            addIfValid(boat, new Position(cell.getRow(), cell.getColumn(), true), positions);
            addIfValid(boat, new Position(cell.getRow(), cell.getColumn(), false), positions);
        }
        return positions;
    }

    private void addIfValid(Boat boat, Position position, List<Position> positions) {
        try {
            validatePlacement(boat, position);
            positions.add(position);
        } catch (BattleshipException e) {
            // posición inválida: no se agrega
        }
    }

    private void clear() {
        for (Cell cell : cells) {
            if (cell.hasBoat()) {
                cell.getBoat().clearCells();
                cell.setBoat(null);
            }
        }
    }

    private void place(Boat boat, Position position) {
        for (int i = 0; i < boat.getSize(); i++) {
            Cell cell = getCell(rowOf(position, i), columnOf(position, i));
            cell.setBoat(boat);
            boat.addCell(cell);
        }
    }

    private boolean fits(Boat boat, Position position) {
        int last = boat.getSize() - 1;
        return isInside(position.getRow(), position.getColumn())
                && isInside(rowOf(position, last), columnOf(position, last));
    }

    private boolean overlaps(Boat boat, Position position) {
        for (int i = 0; i < boat.getSize(); i++) {
            if (getCell(rowOf(position, i), columnOf(position, i)).hasBoat()) {
                return true;
            }
        }
        return false;
    }

    // Cuenta también las diagonales.
    private boolean touches(Boat boat, Position position) {
        for (int i = 0; i < boat.getSize(); i++) {
            if (hasBoatAround(rowOf(position, i), columnOf(position, i))) {
                return true;
            }
        }
        return false;
    }

    private boolean hasBoatAround(int row, int column) {
        for (int r = row - 1; r <= row + 1; r++) {
            for (int c = column - 1; c <= column + 1; c++) {
                if (isInside(r, c) && getCell(r, c).hasBoat()) {
                    return true;
                }
            }
        }
        return false;
    }

    private int rowOf(Position position, int part) {
        return position.isHorizontal() ? position.getRow() : position.getRow() + part;
    }

    private int columnOf(Position position, int part) {
        return position.isHorizontal() ? position.getColumn() + part : position.getColumn();
    }
}
