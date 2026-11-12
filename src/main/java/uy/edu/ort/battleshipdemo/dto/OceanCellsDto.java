package uy.edu.ort.battleshipdemo.dto;

import java.util.ArrayList;
import java.util.List;

import uy.edu.ort.battleshipdemo.ValorDto;
import uy.edu.ort.battleshipdemo.model.Cell;
import uy.edu.ort.battleshipdemo.model.Ocean;

public class OceanCellsDto {

    public static List<ValorDto> fromOcean(Ocean ocean, boolean showBoats) {
        List<ValorDto> values = new ArrayList<>();
        for (Cell cell : ocean.getCells()) {
            String value = cellValue(cell, showBoats);
            if (value != null) {
                values.add(new ValorDto(cell.getRow(), cell.getColumn(), value));
            }
        }
        return values;
    }

    private static String cellValue(Cell cell, boolean showBoats) {
        if (cell.isHit()) {
            return "impacto";
        }
        if (cell.isShot()) {
            return "agua";
        }
        if (cell.hasBoat() && showBoats) {
            return "barco";
        }
        return null;
    }
}
