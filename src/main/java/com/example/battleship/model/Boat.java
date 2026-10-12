package com.example.battleship.model;

public class Boat {
    
    private String name; 
    private int size;
    private enum boatType {SUBMARINE, DESTROYER, CRUISER, BATTLESHIP, CARRIER};

    public Boat(String name, int size, boatType type) {
        this.name = name;
        this.size = size;
    }
}
