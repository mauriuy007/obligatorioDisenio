package uy.edu.ort.battleshipdemo.model;

public class WonStatus extends BattleStatus {

    public WonStatus(Battle battle) {
        super(battle);
    }

    @Override
    public String getName() {
        return "Ganada";
    }

    @Override
    public boolean isWon() {
        return true;
    }

    @Override
    public void startCombat() throws BattleshipException {
        throw new BattleshipException("La batalla ya terminó");
    }

    @Override
    public void placeBoat(int row, int column, boolean horizontal) throws BattleshipException {
        throw new BattleshipException("La batalla ya terminó");
    }

    @Override
    public void fire(int row, int column) throws BattleshipException {
        throw new BattleshipException("La batalla ya terminó");
    }
}
