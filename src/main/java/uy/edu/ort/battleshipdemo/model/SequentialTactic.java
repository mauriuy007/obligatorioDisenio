package uy.edu.ort.battleshipdemo.model;

import java.util.List;

// Dispara en orden: de izquierda a derecha y de abajo hacia arriba.
public class SequentialTactic implements Tactic {

    @Override
    public String getName() {
        return "Secuencial";
    }

    @Override
    public Cell chooseTarget(Ocean ocean) throws BattleshipException {
        List<Cell> unshot = ocean.getUnshotCells();
        if (unshot.isEmpty()) {
            throw new BattleshipException("No quedan celdas a las que disparar");
        }
        return unshot.get(0);
    }
}
