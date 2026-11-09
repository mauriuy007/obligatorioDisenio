package uy.edu.ort.battleshipdemo.model;

import lombok.Getter;

public class TacticType {

    @Getter
    private String name;

    public TacticType(String name) {
        this.name = name;
    }
}
