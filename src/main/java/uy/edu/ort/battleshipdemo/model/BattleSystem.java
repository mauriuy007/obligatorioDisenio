package uy.edu.ort.battleshipdemo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lombok.Getter;

public class BattleSystem {

    private static final int MIN_CELLS = 6;
    private static final int MAX_CELLS = 12;
    private static final int RANDOM_PLACE_LENGTH = 6;

    @Getter
    private List<Battle> battles = new ArrayList<>();
    @Getter
    private List<BoatType> boatTypes = new ArrayList<>();
    @Getter
    private List<TacticType> tacticTypes = new ArrayList<>();
    private Random random = new Random();

    // quantities.get(i) es la cantidad de barcos del tipo boatTypes.get(i).
    public Battle createBattle(String place, TacticType tacticType, int width, int height,
            List<Integer> quantities) throws BattleshipException {
        validateDimensions(width, height);
        String battlePlace = place == null || place.isBlank() ? randomPlace() : place.trim();
        Battle battle = new Battle(battlePlace, tacticType, width, height,
                createFleet(quantities), createFleet(quantities));
        battle.validate();
        battle.deployMachineFleet(random);
        addBattle(battle);
        return battle;
    }

    // Se valida antes de construir la batalla, para no armar océanos de tamaño inválido.
    private void validateDimensions(int width, int height) throws BattleshipException {
        if (width < MIN_CELLS || width > MAX_CELLS) {
            throw new BattleshipException(
                    "La cantidad de celdas horizontales debe estar entre " + MIN_CELLS + " y " + MAX_CELLS);
        }
        if (height < MIN_CELLS || height > MAX_CELLS) {
            throw new BattleshipException(
                    "La cantidad de celdas verticales debe estar entre " + MIN_CELLS + " y " + MAX_CELLS);
        }
    }

    private void addBattle(Battle battle) throws BattleshipException {
        if (existsPlace(battle.getPlace())) {
            throw new BattleshipException("No se puede librar más de una batalla en el mismo lugar, cambie de nombre");
        }
        battles.add(battle);
    }

    private Fleet createFleet(List<Integer> quantities) throws BattleshipException {
        if (quantities.size() != boatTypes.size()) {
            throw new BattleshipException("Indique la cantidad de barcos de cada tipo");
        }
        Fleet fleet = new Fleet();
        for (int i = 0; i < boatTypes.size(); i++) {
            fleet.addBoats(boatTypes.get(i), quantities.get(i));
        }
        return fleet;
    }

    private String randomPlace() {
        String place;
        do {
            place = "";
            for (int i = 0; i < RANDOM_PLACE_LENGTH; i++) {
                place = place + (char) ('A' + random.nextInt(26));
            }
        } while (existsPlace(place));
        return place;
    }

    public void addBoatType(BoatType boatType) {
        boatTypes.add(boatType);
    }

    public void addTacticType(TacticType tacticType) {
        tacticTypes.add(tacticType);
    }

    public boolean existsPlace(String place) {
        for (Battle battle : battles) {
            if (battle.hasPlace(place)) {
                return true;
            }
        }
        return false;
    }

    public Battle getBattle(String place) throws BattleshipException {
        for (Battle battle : battles) {
            if (battle.hasPlace(place)) {
                return battle;
            }
        }
        throw new BattleshipException("No existe una batalla en " + place);
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
