package uy.edu.ort.battleshipdemo.model;

public class LostStatus extends BattleStatus {

    public LostStatus(Battle battle) {
        super(battle);
    }

    @Override
    public String getName() {
        return "Perdida";
    }

    @Override
    public boolean isLost() {
        return true;
    }

    @Override
    public boolean revealsEnemyFleet() {
        return true;
    }

    @Override
    public void startCombat() throws BattleshipException {
        throw new BattleshipException("La batalla ya terminó");
    }

    @Override
    public void fire(int row, int column) throws BattleshipException {
        throw new BattleshipException("La batalla ya terminó");
    }
}
