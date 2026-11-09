package uy.edu.ort.battleshipdemo.model;

import lombok.Getter;

public class BoatType {

    @Getter
    private String name;
    @Getter
    private int size;

    public BoatType(String name, int size) {
        this.name = name;
        this.size = size;
    }
}
