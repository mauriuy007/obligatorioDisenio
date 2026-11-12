package uy.edu.ort.battleshipdemo.model;

public class InCombatStatus extends BattleStatus {

    public InCombatStatus(Battle battle) {
        super(battle);
    }

    @Override
    public String getName() {
        return "En combate";
    }

    @Override
    public void startCombat() throws BattleshipException {
        throw new BattleshipException("La batalla ya comenzó");
    }

    @Override
    public void placeBoat(int row, int column, boolean horizontal) throws BattleshipException {
        throw new BattleshipException("La batalla ya comenzó, no se pueden mover los barcos");
    }

    @Override
    public void fire(int row, int column) throws BattleshipException {
        getBattle().doFire(row, column);
        if (getBattle().getMachine().isDefeated()) {
            getBattle().changeStatus(StatusType.WON);
        } else if (getBattle().getHuman().isDefeated()) {
            getBattle().changeStatus(StatusType.LOST);
        }
    }

    @Override
    public int roundNumber(int playedRounds) {
        return playedRounds + 1;
    }
}
