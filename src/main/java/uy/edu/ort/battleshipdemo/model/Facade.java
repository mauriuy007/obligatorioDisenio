package uy.edu.ort.battleshipdemo.model;

import java.util.List;

import uy.edu.ort.battleshipdemo.observer.Observable;
import uy.edu.ort.battleshipdemo.observer.Observer;

public class Facade extends Observable implements Observer {

    private static Facade instance;

    private BattleSystem battleSystem = new BattleSystem();

    public enum Events {
        NEW_BATTLE,
        BATTLE_UPDATED
    }

    public static Facade getInstance() {
        if (instance == null) {
            instance = new Facade();
        }
        return instance;
    }

    public Battle createBattle(String place, TacticType tacticType, int width, int height,
            List<Integer> quantities) throws BattleshipException {
        Battle battle = battleSystem.createBattle(place, tacticType, width, height, quantities);
        battle.addObserver(this);
        notifyObservers(Events.NEW_BATTLE);
        return battle;
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

    @Override
    public void update(Observable source, Object event) {
        if (event.equals(Battle.Events.COMBAT_STARTED) || event.equals(Battle.Events.ROUND_PLAYED)) {
            notifyObservers(Events.BATTLE_UPDATED);
        }
    }
}
