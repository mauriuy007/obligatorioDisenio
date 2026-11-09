package uy.edu.ort.battleshipdemo.model;

import java.util.ArrayList;
import java.util.List;

// Arranca en el centro y gira en sentido horario con tramos de largo 1, 1, 2, 2, 3, 3...
// Las celdas que caen fuera del océano se saltean.
public class SpiralTactic implements Tactic {

    @Override
    public String getName() {
        return "Espiral";
    }

    @Override
    public Cell chooseTarget(Ocean ocean) throws BattleshipException {
        for (Cell cell : spiralOrder(ocean)) {
            if (!cell.isShot()) {
                return cell;
            }
        }
        throw new BattleshipException("No quedan celdas a las que disparar");
    }

    private List<Cell> spiralOrder(Ocean ocean) {
        List<Cell> order = new ArrayList<>();
        int row = ocean.getHeight() / 2;
        int column = (ocean.getWidth() - 1) / 2;
        addIfInside(ocean, order, row, column);
        int length = 1;
        int direction = 0;
        while (order.size() < ocean.getSize()) {
            for (int side = 0; side < 2; side++) {
                for (int step = 0; step < length; step++) {
                    switch (direction) {
                        case 0:
                            column++; // derecha
                            break;
                        case 1:
                            row--; // abajo
                            break;
                        case 2:
                            column--; // izquierda
                            break;
                        default:
                            row++; // arriba
                            break;
                    }
                    addIfInside(ocean, order, row, column);
                }
                direction = (direction + 1) % 4;
            }
            length++;
        }
        return order;
    }

    private void addIfInside(Ocean ocean, List<Cell> order, int row, int column) {
        if (ocean.isInside(row, column)) {
            order.add(ocean.getCell(row, column));
        }
    }
}
