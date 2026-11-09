package uy.edu.ort.battleshipdemo.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import uy.edu.ort.battleshipdemo.model.BoatType;

public class BoatTypeDto {

    @Getter
    private String name;
    @Getter
    private int size;

    public BoatTypeDto(BoatType boatType) {
        this.name = boatType.getName();
        this.size = boatType.getSize();
    }

    public static List<BoatTypeDto> fromList(List<BoatType> boatTypes) {
        List<BoatTypeDto> rows = new ArrayList<>();
        for (BoatType boatType : boatTypes) {
            rows.add(new BoatTypeDto(boatType));
        }
        return rows;
    }
}
