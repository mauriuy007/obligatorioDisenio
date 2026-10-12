package com.example.battleship.model;
import com.example.battleship.exception.InvalidActionException;

public abstract class BattleState {

    public abstract String getName();

    public void placeBoat(Battle battle, int row, int column, String orientation) {
        throw new InvalidActionException("No se puede ubicar barcos en este estado");
    }

    public void startCombat(Battle battle) {
        throw new InvalidActionException("La batalla ya no está en preparación");
    }

    public void fire(Battle battle, int row, int column) {
        throw new InvalidActionException("No se puede disparar en este estado");
    }
}
