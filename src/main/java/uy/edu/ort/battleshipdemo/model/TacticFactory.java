package uy.edu.ort.battleshipdemo.model;

public class TacticFactory {

    public static Tactic createTactic(TacticType tacticType) {

        switch (tacticType.getName()) {
            case "Secuencial":
                return new SequentialTactic();
            case "Espiral":
                return new SpiralTactic();
            case "Caza y ataque":
                return new HuntAndAttackTactic();
            default:
                return new SequentialTactic();
        }
    }
}
