package uy.edu.ort.battleshipdemo.dto;

import lombok.Getter;
import uy.edu.ort.battleshipdemo.model.Battle;

public class BattleDto {

    @Getter
    private String place;

    public BattleDto(Battle battle) {
        this.place = battle.getPlace();
    }
}
