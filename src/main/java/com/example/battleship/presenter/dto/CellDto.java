package com.example.battleship.presenter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Parameter of the "mostrarAguasPropias" / "mostrarAguasEnemigas" commands.
 * The JSON keys are fixed by BattleShipBoard.js.
 */
@Data
public class CellDto {
    @JsonProperty("coordenadaVertical")
    private int row;
    @JsonProperty("coordenadaHorizontal")
    private int column;
    @JsonProperty("valor")
    private String value;

    public CellDto(int row, int column, String value) {
        this.row = row;
        this.column = column;
        this.value = value;
    }
}
