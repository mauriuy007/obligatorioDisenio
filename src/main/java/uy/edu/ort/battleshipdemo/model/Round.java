package uy.edu.ort.battleshipdemo.model;

public class Round {

    private Shot humanShot;
    private Shot machineShot;

    public Round(Shot humanShot) {
        this.humanShot = humanShot;
    }

    public void setMachineShot(Shot machineShot) {
        this.machineShot = machineShot;
    }
}
