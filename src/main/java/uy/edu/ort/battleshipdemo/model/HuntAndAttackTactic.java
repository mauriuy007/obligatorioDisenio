package uy.edu.ort.battleshipdemo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// Caza: sin impactos sobre barcos a flote, dispara a una celda aleatoria.
// Ataque: con impactos, dispara a las celdas vecinas; si hay dos impactos alineados, sigue esa línea.
// Al hundirse el barco vuelve sola al modo caza.
public class HuntAndAttackTactic implements Tactic {

    private Random random = new Random();

    @Override
    public String getName() {
        return "Caza y ataque";
    }

    @Override
    public Cell chooseTarget(Ocean ocean) throws BattleshipException {
        List<Cell> candidates = attack(ocean);
        if (candidates.isEmpty()) {
            candidates = ocean.getUnshotCells();
        }
        return randomOf(candidates);
    }

    private List<Cell> attack(Ocean ocean) {
        List<Cell> hits = ocean.getOpenHits();
        List<Cell> lineEnds = lineEnds(ocean, hits);
        if (!lineEnds.isEmpty()) {
            return lineEnds;
        }
        return neighbours(ocean, hits);
    }

    private List<Cell> lineEnds(Ocean ocean, List<Cell> hits) {
        List<Cell> ends = new ArrayList<>();
        for (Cell hit : hits) {
            addLineEnd(ocean, hit, 0, 1, ends);
            addLineEnd(ocean, hit, -1, 0, ends);
            addLineEnd(ocean, hit, 0, -1, ends);
            addLineEnd(ocean, hit, 1, 0, ends);
        }
        return ends;
    }

    private void addLineEnd(Ocean ocean, Cell from, int rowStep, int columnStep, List<Cell> ends) {
        if (!ocean.isOpenHit(from.getRow() + rowStep, from.getColumn() + columnStep)) {
            return;
        }
        int row = from.getRow();
        int column = from.getColumn();
        while (ocean.isOpenHit(row, column)) {
            row += rowStep;
            column += columnStep;
        }
        addIfUnshot(ocean, row, column, ends);
    }

    private List<Cell> neighbours(Ocean ocean, List<Cell> hits) {
        List<Cell> neighbours = new ArrayList<>();
        for (Cell hit : hits) {
            addIfUnshot(ocean, hit.getRow(), hit.getColumn() + 1, neighbours);
            addIfUnshot(ocean, hit.getRow() - 1, hit.getColumn(), neighbours);
            addIfUnshot(ocean, hit.getRow(), hit.getColumn() - 1, neighbours);
            addIfUnshot(ocean, hit.getRow() + 1, hit.getColumn(), neighbours);
        }
        return neighbours;
    }

    private void addIfUnshot(Ocean ocean, int row, int column, List<Cell> cells) {
        if (ocean.isUnshot(row, column)) {
            Cell cell = ocean.getCell(row, column);
            if (!cells.contains(cell)) {
                cells.add(cell);
            }
        }
    }

    private Cell randomOf(List<Cell> cells) throws BattleshipException {
        if (cells.isEmpty()) {
            throw new BattleshipException("No quedan celdas a las que disparar");
        }
        return cells.get(random.nextInt(cells.size()));
    }
}
