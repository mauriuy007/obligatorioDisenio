package uy.edu.ort.battleshipdemo.model;

public class BattleStatusFactory {

    public static BattleStatus createStatus(StatusType type, Battle battle) {

        switch (type.getName()) {
            case "En preparación":
                return new PreparingStatus(battle);
            case "En combate":
                return new InCombatStatus(battle);
            case "Ganada":
                return new WonStatus(battle);
            case "Perdida":
                return new LostStatus(battle);
            default:
                return new PreparingStatus(battle);
        }
    }
}
