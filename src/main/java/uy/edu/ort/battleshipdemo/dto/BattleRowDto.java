package uy.edu.ort.battleshipdemo.dto;

import lombok.Data;
import uy.edu.ort.battleshipdemo.model.Battle;
import uy.edu.ort.battleshipdemo.model.Fleet;

@Data
public class BattleRowDto {
    private String place;
    private String dimensions;
    private String status;
    private String ownFleetIntegrity;
    private String enemyFleetIntegrity;

    public BattleRowDto(Battle battle) {
        this.place = battle.getPlace();
        this.dimensions = battle.getWidth() + " x " + battle.getHeight();
        this.status = battle.getStatusName();
        this.ownFleetIntegrity = integrity(battle.getFleet());
        this.enemyFleetIntegrity = integrity(battle.getEnemyFleet());
    }

    private static String integrity(Fleet fleet) {
        return fleet.getHealthyParts() + "/" + fleet.getTotalSize();
    }
}
