package uy.edu.ort.battleshipdemo.dto;

import lombok.Data;
import uy.edu.ort.battleshipdemo.model.BoatType;

@Data
public class BoatTypeDto {
    private String name;
    private int size;

    public BoatTypeDto(BoatType boatType) {
        this.name = boatType.getName();
        this.size = boatType.getSize();
    }
}
