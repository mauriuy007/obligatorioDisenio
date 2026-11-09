package uy.edu.ort.battleshipdemo.model;

import lombok.Getter;

public class Position {

    @Getter
    private int row;
    @Getter
    private int column;
    @Getter
    private boolean horizontal;

    public Position(int row, int column, boolean horizontal) {
        this.row = row;
        this.column = column;
        this.horizontal = horizontal;
    }
}
