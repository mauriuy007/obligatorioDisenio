package uy.edu.ort.battleshipdemo.model;

public interface Tactic {

    public Cell chooseTarget(Ocean ocean) throws BattleshipException;
}
