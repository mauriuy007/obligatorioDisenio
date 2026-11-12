package uy.edu.ort.battleshipdemo.model;

public interface Tactic {

    public String getName();

    public Cell chooseTarget(Ocean ocean) throws BattleshipException;
}
