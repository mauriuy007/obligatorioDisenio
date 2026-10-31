package uy.edu.ort.battleshipdemo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import uy.edu.ort.battleshipdemo.exception.BattleException;
import uy.edu.ort.battleshipdemo.exception.FleetException;
import uy.edu.ort.battleshipdemo.exception.InvalidActionException;
import uy.edu.ort.battleshipdemo.exception.TacticException;

public class BattleSystem {

    private static final int RANDOM_PLACE_LENGTH = 6;

    private List<Battle> battles = new ArrayList<>();
    private List<BoatType> boatTypes = new ArrayList<>();
    private List<Tactic> tactics = new ArrayList<>();
    private Random random = new Random();

    public List<Battle> getBattles() {
        return new ArrayList<>(battles);
    }

    public void addBattle(Battle battle) throws BattleException {
        if (existsPlace(battle.getPlace())) {
            throw BattleException.duplicatePlace();
        }
        battles.add(battle);
    }

    public Battle createBattle(String place, String tacticName, int width, int height,
            Map<String, Integer> quantities) throws InvalidActionException {
        Battle battle = new Battle();
        battle.setPlace(place == null || place.isBlank() ? randomPlace() : place.trim());
        battle.setTactic(getTactic(tacticName));
        battle.setWidth(width);
        battle.setHeight(height);
        battle.setFleet(createFleet(quantities));
        battle.setEnemyFleet(createFleet(quantities));
        battle.setStatus(new PreparingStatus());
        battle.validate();
        addBattle(battle);
        return battle;
    }

    private Fleet createFleet(Map<String, Integer> quantities) throws FleetException {
        for (String name : quantities.keySet()) {
            getBoatType(name);
        }
        Fleet fleet = new Fleet();
        for (BoatType type : boatTypes) {
            Integer quantity = quantities.get(type.getName());
            fleet.addBoats(type, quantity == null ? 0 : quantity);
        }
        return fleet;
    }

    private String randomPlace() {
        String place;
        do {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < RANDOM_PLACE_LENGTH; i++) {
                builder.append((char) ('A' + random.nextInt(26)));
            }
            place = builder.toString();
        } while (existsPlace(place));
        return place;
    }

    public List<BoatType> getBoatTypes() {
        return new ArrayList<>(boatTypes);
    }

    public void addBoatType(BoatType boatType) {
        boatTypes.add(boatType);
    }

    public BoatType getBoatType(String name) throws FleetException {
        for (BoatType boatType : boatTypes) {
            if (boatType.getName().equalsIgnoreCase(name)) {
                return boatType;
            }
        }
        throw FleetException.unknownBoatType(name);
    }

    public List<Tactic> getTactics() {
        return new ArrayList<>(tactics);
    }

    public void addTactic(Tactic tactic) {
        tactics.add(tactic);
    }

    public Tactic getTactic(String name) throws TacticException {
        for (Tactic tactic : tactics) {
            if (tactic.getName().equalsIgnoreCase(name)) {
                return tactic;
            }
        }
        throw TacticException.notFound(name);
    }

    public boolean existsPlace(String place) {
        for (Battle battle : battles) {
            if (battle.hasPlace(place)) {
                return true;
            }
        }
        return false;
    }

    public Battle getBattle(String place) throws BattleException {
        for (Battle battle : battles) {
            if (battle.hasPlace(place)) {
                return battle;
            }
        }
        throw BattleException.notFound(place);
    }

    public int getWonCount() {
        int count = 0;
        for (Battle battle : battles) {
            if (battle.isWon()) {
                count++;
            }
        }
        return count;
    }

    public int getLostCount() {
        int count = 0;
        for (Battle battle : battles) {
            if (battle.isLost()) {
                count++;
            }
        }
        return count;
    }

    public int getSuccessPercentage() {
        if (battles.isEmpty()) {
            return 0;
        }
        return (int) Math.round(getWonCount() * 100.0 / battles.size());
    }
}
