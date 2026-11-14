package uy.edu.ort.battleshipdemo.model;

import java.util.List;

import lombok.Getter;
import uy.edu.ort.battleshipdemo.observer.Observable;

public class Battle extends Observable {

    public enum Events {
        BOAT_PLACED,
        COMBAT_STARTED,
        ROUND_PLAYED
    }

    private static final int MIN_CELLS = 6;
    private static final int MAX_CELLS = 12;

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
    private int playedRounds;
    private BattleStatus status;

    public Battle(String place, TacticType tacticType, int width, int height, Fleet humanFleet, Fleet machineFleet)
            throws BattleshipException {
        validateDimensions(width, height);
        this.place = place;
        this.width = width;
        this.height = height;
        this.human = new Player(humanFleet, width, height);
        this.machine = new Machine(machineFleet, width, height, TacticFactory.createTactic(tacticType));
        this.status = new PreparingStatus(this);
    }

    // Se valida antes de crear los océanos, para no armar celdas de medidas inválidas.
    private void validateDimensions(int width, int height) throws BattleshipException {
        if (width < MIN_CELLS || width > MAX_CELLS) {
            throw new BattleshipException(
                    "La cantidad de celdas horizontales debe estar entre " + MIN_CELLS + " y " + MAX_CELLS);
        }
        if (height < MIN_CELLS || height > MAX_CELLS) {
            throw new BattleshipException(
                    "La cantidad de celdas verticales debe estar entre " + MIN_CELLS + " y " + MAX_CELLS);
        }
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

    public void deployMachineFleet() {
        machine.deployRandomly();
    }

    public void changeStatus(StatusType type) {
        this.status = BattleStatusFactory.createStatus(type, this);
    }

    public void placeBoat(int row, int column, boolean horizontal) throws BattleshipException {
        status.placeBoat(row, column, horizontal);
        notifyObservers(Events.BOAT_PLACED);
    }

    protected void doPlaceBoat(int row, int column, boolean horizontal) throws BattleshipException {
        human.placeNextBoat(row, column, horizontal);
    }

    public void placeBoatInEnemyWaters() throws BattleshipException {
        throw new BattleshipException("No puede ubicar barcos en aguas enemigas");
    }

    public void startCombat() throws BattleshipException {
        status.startCombat();
        notifyObservers(Events.COMBAT_STARTED);
    }

    protected void doStartCombat() throws BattleshipException {
        if (!human.isDeployed()) {
            throw new BattleshipException("Debe posicionar todos sus barcos primero");
        }
    }

    public void fire(int row, int column) throws BattleshipException {
        status.fire(row, column);
        notifyObservers(Events.ROUND_PLAYED);
    }

    protected void doFire(int row, int column) throws BattleshipException {
        machine.receiveShot(row, column);
        playedRounds++;
        if (!machine.isDefeated()) {
            machine.fireAt(human);
        }
    }

    public void fireAtOwnWaters() throws BattleshipException {
        throw new BattleshipException("No puede disparar a sus propias aguas");
    }

    public List<Boat> getUnplacedBoats() {
        return human.getUnplacedBoats();
    }

    public boolean hasPlace(String place) {
        return this.place != null && this.place.equalsIgnoreCase(place);
    }

    public int getRoundNumber() {
        return status.roundNumber(playedRounds);
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
