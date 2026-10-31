package uy.edu.ort.battleshipdemo.model;

import java.util.List;

import uy.edu.ort.battleshipdemo.exception.BattleException;

public class Facade {

    private static final Facade instance = new Facade();

    private BattleSystem battleSystem = new BattleSystem();

    private Facade() {
    }

    public static Facade getInstance() {
        return instance;
    }

    public void addBattle(Battle battle) throws BattleException {
        battleSystem.addBattle(battle);
    }

    public List<Battle> getBattles() {
        return battleSystem.getBattles();
    }

    public int getWonCount() {
        return battleSystem.getWonCount();
    }

    public int getLostCount() {
        return battleSystem.getLostCount();
    }

    public int getSuccessPercentage() {
        return battleSystem.getSuccessPercentage();
    }
}
