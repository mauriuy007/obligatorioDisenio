package uy.edu.ort.battleshipdemo.model;

public class LostStatus extends BattleStatus {

    @Override
    public String getName() {
        return "Perdida";
    }

    @Override
    public boolean isLost() {
        return true;
    }
}
