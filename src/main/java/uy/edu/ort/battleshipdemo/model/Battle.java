package uy.edu.ort.battleshipdemo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lombok.Getter;

// Las acciones de la batalla (iniciar combate, disparar) las resuelve su estado (patrón State).
public class Battle {

    @Getter
    private String place;
    @Getter
    private int width;
    @Getter
    private int height;
    @Getter
    private Player human;
    @Getter
    private Machine machine;
    private List<Round> rounds = new ArrayList<>();
    private BattleStatus status;

    public Battle(String place, TacticType tacticType, int width, int height, Fleet humanFleet, Fleet machineFleet) {
        this.place = place;
        this.width = width;
        this.height = height;
        this.human = new Player(humanFleet, width, height);
        this.machine = new Machine(machineFleet, width, height, TacticFactory.createTactic(tacticType));
        this.status = new PreparingStatus(this);
    }

    public void validate() throws BattleshipException {
        Fleet fleet = human.getFleet();
        fleet.validate();
        int oceanCells = width * height;
        if (fleet.getTotalSize() * 4 >= oceanCells) {
            throw new BattleshipException("La flota no entra en el océano: ocupa " + fleet.getTotalSize() + " de "
                    + oceanCells + " celdas y debe ocupar menos del 25%");
        }
    }

    public void deployMachineFleet(Random random) {
        machine.deployRandomly(random);
    }

    public void changeStatus(StatusType type) {
        this.status = BattleStatusFactory.createStatus(type, this);
    }

    public void startCombat() throws BattleshipException {
        status.startCombat();
    }

    // Lo invoca PreparingStatus.
    protected void doStartCombat() throws BattleshipException {
        if (!human.isDeployed()) {
            throw new BattleshipException("Debe posicionar todos sus barcos primero");
        }
    }

    public void fire(int row, int column) throws BattleshipException {
        status.fire(row, column);
    }

    // Lo invoca InCombatStatus: disparo del humano y, si la flota enemiga sigue a flote, respuesta de la máquina.
    protected void doFire(int row, int column) throws BattleshipException {
        Round round = new Round(new Shot(machine.receiveShot(row, column)));
        rounds.add(round);
        if (!machine.isDefeated()) {
            round.setMachineShot(new Shot(machine.fireAt(human)));
        }
    }

    public boolean hasPlace(String place) {
        return this.place != null && this.place.equalsIgnoreCase(place);
    }

    public int getRoundNumber() {
        return status.roundNumber(rounds.size());
    }

    public String getStatusName() {
        return status.getName();
    }

    public boolean isPreparing() {
        return status.isPreparing();
    }

    public boolean isWon() {
        return status.isWon();
    }

    public boolean isLost() {
        return status.isLost();
    }

    public boolean revealsEnemyFleet() {
        return status.revealsEnemyFleet();
    }
}
