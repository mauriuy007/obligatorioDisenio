package uy.edu.ort.battleshipdemo.model;

import java.util.List;
import java.util.Map;

import uy.edu.ort.battleshipdemo.exception.BattleException;
import uy.edu.ort.battleshipdemo.exception.InvalidActionException;

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

    public Battle createBattle(String place, String tacticName, int width, int height,
            Map<String, Integer> quantities) throws InvalidActionException {
        return battleSystem.createBattle(place, tacticName, width, height, quantities);
    }

    public List<BoatType> getBoatTypes() {
        return battleSystem.getBoatTypes();
    }

    public void addBoatType(BoatType boatType) {
        battleSystem.addBoatType(boatType);
    }

    public List<Tactic> getTactics() {
        return battleSystem.getTactics();
    }

    public void addTactic(Tactic tactic) {
        battleSystem.addTactic(tactic);
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
