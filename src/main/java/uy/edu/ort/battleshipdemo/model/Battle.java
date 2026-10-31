package uy.edu.ort.battleshipdemo.model;

import java.util.ArrayList;
import java.util.List;

public class Battle {

    private String place;
    private int width;
    private int height;
    private List<Round> rounds = new ArrayList<>();
    private Fleet fleet;
    private Fleet enemyFleet;
    private BattleStatus status;

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public boolean hasPlace(String place) {
        return this.place != null && this.place.equalsIgnoreCase(place);
    }

    public boolean isWon() {
        return status.isWon();
    }

    public boolean isLost() {
        return status.isLost();
    }

    public String getStatusName() {
        return status.getName();
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

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

    public Fleet getEnemyFleet() {
        return enemyFleet;
    }

    public void setEnemyFleet(Fleet enemyFleet) {
        this.enemyFleet = enemyFleet;
    }

    public BattleStatus getStatus() {
        return status;
    }

    public void setStatus(BattleStatus status) {
        this.status = status;
    }
}
