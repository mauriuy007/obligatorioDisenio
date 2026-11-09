package uy.edu.ort.battleshipdemo.model;

// Patrón Strategy: cada táctica decide a qué celda dispara la máquina. Las crea TacticFactory.
public interface Tactic {

    public String getName();

    public Cell chooseTarget(Ocean ocean) throws BattleshipException;
}
