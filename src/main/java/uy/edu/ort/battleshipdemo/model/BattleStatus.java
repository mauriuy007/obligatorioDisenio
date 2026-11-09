package uy.edu.ort.battleshipdemo.model;

import lombok.Getter;

// Patrón State: cada estado decide qué acciones permite y a qué estado pasa la batalla después.
public abstract class BattleStatus {

    @Getter
    private Battle battle;

    public BattleStatus(Battle battle) {
        this.battle = battle;
    }

    public abstract String getName();

    public abstract void startCombat() throws BattleshipException;

    public abstract void fire(int row, int column) throws BattleshipException;

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
