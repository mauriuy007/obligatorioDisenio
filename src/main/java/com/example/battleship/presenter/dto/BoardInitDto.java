package com.example.battleship.presenter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Parameter of the "inicializar" command. The JSON keys are fixed by
 * BattleShipBoard.js.
 */
@Data
public class BoardInitDto {
    @JsonProperty("numeroCeldasVerticales")
    private int rows;
    @JsonProperty("numeroCeldasHorizontales")
    private int columns;
    @JsonProperty("escuchadorOceanoPropio")
    private String ownWatersListener;
    @JsonProperty("escuchadorOceanoEnemigo")
    private String enemyWatersListener;

    public BoardInitDto(int rows, int columns) {
        this(rows, columns, null, null);
    }

    public BoardInitDto(int rows, int columns, String ownWatersListener, String enemyWatersListener) {
        this.rows = rows;
        this.columns = columns;
        this.ownWatersListener = ownWatersListener;
        this.enemyWatersListener = enemyWatersListener;
    }
}
