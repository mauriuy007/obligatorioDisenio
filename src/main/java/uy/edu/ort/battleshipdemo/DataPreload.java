package uy.edu.ort.battleshipdemo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import uy.edu.ort.battleshipdemo.exception.BattleException;
import uy.edu.ort.battleshipdemo.model.Battle;
import uy.edu.ort.battleshipdemo.model.BattleStatus;
import uy.edu.ort.battleshipdemo.model.Boat;
import uy.edu.ort.battleshipdemo.model.BoatType;
import uy.edu.ort.battleshipdemo.model.Facade;
import uy.edu.ort.battleshipdemo.model.Fleet;
import uy.edu.ort.battleshipdemo.model.InCombatStatus;
import uy.edu.ort.battleshipdemo.model.LostStatus;
import uy.edu.ort.battleshipdemo.model.PreparingStatus;
import uy.edu.ort.battleshipdemo.model.WonStatus;

/**
 * Carga los datos iniciales del sistema al arrancar la aplicación.
 */
@Component
public class DataPreload implements CommandLineRunner {

    private final BoatType carrier = new BoatType("Portaaviones", 5);
    private final BoatType battleship = new BoatType("Acorazado", 4);
    private final BoatType cruiser = new BoatType("Crucero", 3);
    private final BoatType submarine = new BoatType("Submarino", 2);
    private final BoatType destroyer = new BoatType("Destructor", 1);

    @Override
    public void run(String... args) throws BattleException {
        Facade facade = Facade.getInstance();

        facade.addBattle(createBattle("Mar del Plata", 10, 10, new PreparingStatus(), cruiser, submarine));
        facade.addBattle(createBattle("Atlántico Sur", 12, 8, new InCombatStatus(), battleship, cruiser, destroyer));
        facade.addBattle(createBattle("Golfo de Vizcaya", 10, 10, new WonStatus(), carrier, submarine));
        facade.addBattle(createBattle("Río de la Plata", 8, 8, new WonStatus(), cruiser, submarine, destroyer));
        facade.addBattle(createBattle("Mar Caribe", 12, 12, new LostStatus(), carrier, battleship, submarine));
    }

    private Battle createBattle(String place, int width, int height, BattleStatus status, BoatType... boatTypes) {
        Battle battle = new Battle();
        battle.setPlace(place);
        battle.setWidth(width);
        battle.setHeight(height);
        battle.setStatus(status);
        battle.setFleet(createFleet(boatTypes));
        battle.setEnemyFleet(createFleet(boatTypes));
        return battle;
    }

    private Fleet createFleet(BoatType... boatTypes) {
        List<Boat> boats = new ArrayList<>();
        for (BoatType type : boatTypes) {
            Boat boat = new Boat();
            boat.setType(type);
            boats.add(boat);
        }
        Fleet fleet = new Fleet();
        fleet.setBoats(boats);
        return fleet;
    }
}
