package uy.edu.ort.battleshipdemo.model;

public class PreparingStatus extends BattleStatus {

    public PreparingStatus(Battle battle) {
        super(battle);
    }

    @Override
    public String getName() {
        return "En preparación";
    }

    @Override
    public boolean isPreparing() {
        return true;
    }

    @Override
    public void startCombat() throws BattleshipException {
        getBattle().doStartCombat();
        getBattle().changeStatus(StatusType.IN_COMBAT);
    }

    @Override
    public void placeBoat(int row, int column, boolean horizontal) throws BattleshipException {
        getBattle().doPlaceBoat(row, column, horizontal);
    }

    @Override
    public void fire(int row, int column) throws BattleshipException {
        throw new BattleshipException("La batalla todavía está en preparación");
    }
}
