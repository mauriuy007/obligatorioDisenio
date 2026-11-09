package uy.edu.ort.battleshipdemo.model;

import lombok.Getter;

public class StatusType {

    public static final StatusType PREPARING = new StatusType("En preparación");
    public static final StatusType IN_COMBAT = new StatusType("En combate");
    public static final StatusType WON = new StatusType("Ganada");
    public static final StatusType LOST = new StatusType("Perdida");

    @Getter
    private String name;

    public StatusType(String name) {
        this.name = name;
    }
}
