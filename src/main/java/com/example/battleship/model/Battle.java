package com.example.battleship.model;

import java.util.ArrayList;
import java.util.List;

public class Battle {

    private List<Round> rounds = new ArrayList<>();
    private Fleet fleet;
    private BattleStatus status;

    public List<Round> getRounds() {
        return rounds;
    }

    public void setRounds(List<Round> rounds) {
        this.rounds = rounds;
    }

    public Fleet getFleet() {
        return fleet;
    }

    public void setFleet(Fleet fleet) {
        this.fleet = fleet;
    }

    public BattleStatus getStatus() {
        return status;
    }

    public void setStatus(BattleStatus status) {
        this.status = status;
    }
}
