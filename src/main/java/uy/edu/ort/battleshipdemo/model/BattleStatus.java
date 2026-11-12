package uy.edu.ort.battleshipdemo.model;

import lombok.Getter;

public abstract class BattleStatus {

    @Getter
    private Battle battle;

    public BattleStatus(Battle battle) {
        this.battle = battle;
    }

    public abstract String getName();

    public abstract void startCombat() throws BattleshipException;

    public abstract void fire(int row, int column) throws BattleshipException;

    public abstract void placeBoat(int row, int column, boolean horizontal) throws BattleshipException;

    public boolean isPreparing() {
        return false;
    }

    public boolean isWon() {
        return false;
    }

    public boolean isLost() {
        return false;
    }

    public int roundNumber(int playedRounds) {
        return playedRounds;
    }

    public boolean revealsEnemyFleet() {
        return false;
    }
}
