package uy.edu.ort.battleshipdemo.dto;

import lombok.Getter;
import uy.edu.ort.battleshipdemo.model.Battle;

// Es lo que se guarda en la sesión al elegir o crear una batalla.
public class BattleDto {

    @Getter
    private String place;

    public BattleDto(Battle battle) {
        this.place = battle.getPlace();
    }
}
