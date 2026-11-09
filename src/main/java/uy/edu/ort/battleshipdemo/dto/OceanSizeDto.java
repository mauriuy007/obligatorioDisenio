package uy.edu.ort.battleshipdemo.dto;

import lombok.Getter;
import uy.edu.ort.battleshipdemo.model.Battle;

public class OceanSizeDto {

    @Getter
    private int rows;
    @Getter
    private int columns;

    public OceanSizeDto(Battle battle) {
        this.rows = battle.getHeight();
        this.columns = battle.getWidth();
    }
}
