package com.example.battleship.model;

import java.util.ArrayList;
import java.util.List;

import com.example.battleship.exception.FleetException;

public class BoatSystem {

    private List<BoatType> boatTypes = new ArrayList<>();

    public List<BoatType> getBoatTypes() {
        return new ArrayList<>(boatTypes);
    }

    public void addBoatType(BoatType boatType) {
        boatTypes.add(boatType);
    }

    public BoatType getBoatType(String name) throws FleetException {
        for (BoatType boatType : boatTypes) {
            if (boatType.hasName(name)) {
                return boatType;
            }
        }
        throw FleetException.unknownBoatType(name);
    }
}
