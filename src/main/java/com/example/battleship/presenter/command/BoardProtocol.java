package com.example.battleship.presenter.command;

/**
 * Names understood by BattleShipBoard.js. They are in Spanish because the
 * course library defines them and it must not be modified.
 */
public final class BoardProtocol {

    // Command ids handled by BattleShipBoard.js
    public static final String INIT = "inicializar";
    public static final String SHOW_OWN_WATERS = "mostrarAguasPropias";
    public static final String SHOW_ENEMY_WATERS = "mostrarAguasEnemigas";
    public static final String ENEMY_CASUALTIES = "bajasEnemigas";

    // Request parameters sent by BattleShipBoard.js when a cell is clicked
    public static final String ROW_PARAM = "coordenadaVertical";
    public static final String COLUMN_PARAM = "coordenadaHorizontal";

    // Cell values with a built-in icon
    public static final String SHIP = "barco";
    public static final String WATER = "agua";
    public static final String HIT = "impacto";

    private BoardProtocol() {
    }
}
