package uy.edu.ort.battleshipdemo.model;

import java.util.List;

public class Facade {

    private static Facade instance;

    private BattleSystem battleSystem = new BattleSystem();

    public static Facade getInstance() {
        if (instance == null) {
            instance = new Facade();
        }
        return instance;
    }

    public Battle createBattle(String place, TacticType tacticType, int width, int height,
            List<Integer> quantities) throws BattleshipException {
        return battleSystem.createBattle(place, tacticType, width, height, quantities);
    }

    public List<BoatType> getBoatTypes() {
        return battleSystem.getBoatTypes();
    }

    public void addBoatType(BoatType boatType) {
        battleSystem.addBoatType(boatType);
    }

    public List<TacticType> getTacticTypes() {
        return battleSystem.getTacticTypes();
    }

    public void addTacticType(TacticType tacticType) {
        battleSystem.addTacticType(tacticType);
    }

    public List<Battle> getBattles() {
        return battleSystem.getBattles();
    }

    public Battle getBattle(String place) throws BattleshipException {
        return battleSystem.getBattle(place);
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
