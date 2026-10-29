package com.example.battleship.model;

import java.util.ArrayList;
import java.util.List;

import com.example.battleship.exception.BattleException;

public class BattleSystem {

    private List<Battle> battles = new ArrayList<>();

    public List<Battle> getBattles() {
        return new ArrayList<>(battles);
    }

    public void addBattle(Battle battle) throws BattleException {
        if (existsPlace(battle.getPlace())) {
            throw BattleException.duplicatePlace();
        }
        battles.add(battle);
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

    public int getFinishedCount() {
        return getWonCount() + getLostCount();
    }

    public int getSuccessPercentage() {
        int finished = getFinishedCount();
        if (finished == 0) {
            return 0;
        }
        return (int) Math.round(getWonCount() * 100.0 / finished);
    }
}
