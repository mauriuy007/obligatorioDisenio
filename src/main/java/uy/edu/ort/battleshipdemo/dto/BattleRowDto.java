package uy.edu.ort.battleshipdemo.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import uy.edu.ort.battleshipdemo.model.Battle;
import uy.edu.ort.battleshipdemo.model.Fleet;

public class BattleRowDto {

    @Getter
    private String place;
    @Getter
    private String dimensions;
    @Getter
    private String status;
    @Getter
    private String ownFleetIntegrity;
    @Getter
    private String enemyFleetIntegrity;

    public BattleRowDto(Battle battle) {
        this.place = battle.getPlace();
        this.dimensions = battle.getWidth() + " x " + battle.getHeight();
        this.status = battle.getStatusName();
        this.ownFleetIntegrity = integrity(battle.getHuman().getFleet());
        this.enemyFleetIntegrity = integrity(battle.getMachine().getFleet());
    }

    public static List<BattleRowDto> fromList(List<Battle> battles) {
        List<BattleRowDto> rows = new ArrayList<>();
        for (Battle battle : battles) {
            rows.add(new BattleRowDto(battle));
        }
        return rows;
    }

    private String integrity(Fleet fleet) {
        return fleet.getHealthyParts() + "/" + fleet.getTotalSize();
    }
}
