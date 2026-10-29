package com.example.battleship.model;

public class BoatType {

    private String name;
    private int size;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean hasName(String name) {
        return this.name != null && this.name.equalsIgnoreCase(name);
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
