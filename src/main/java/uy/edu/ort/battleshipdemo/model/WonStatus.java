package uy.edu.ort.battleshipdemo.model;

public class WonStatus extends BattleStatus {

    @Override
    public String getName() {
        return "Ganada";
    }

    @Override
    public boolean isWon() {
        return true;
    }
}
