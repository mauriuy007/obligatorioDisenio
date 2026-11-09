package uy.edu.ort.battleshipdemo.dto;

import lombok.Getter;
import uy.edu.ort.battleshipdemo.model.Battle;

public class BattleHeaderDto {

    @Getter
    private String place;
    @Getter
    private String status;
    @Getter
    private int round;
    @Getter
    private boolean preparing;

    public BattleHeaderDto(Battle battle) {
        this.place = battle.getPlace();
        this.status = battle.getStatusName();
        this.round = battle.getRoundNumber();
        this.preparing = battle.isPreparing();
    }
}
