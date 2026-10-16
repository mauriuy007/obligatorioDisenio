package com.example.battleship.model;

public class Round {

    private Shot humanShot;
    private Shot machineShot;

    public Shot getHumanShot() {
        return humanShot;
    }

    public void setHumanShot(Shot humanShot) {
        this.humanShot = humanShot;
    }

    public Shot getMachineShot() {
        return machineShot;
    }

    public void setMachineShot(Shot machineShot) {
        this.machineShot = machineShot;
    }
}
