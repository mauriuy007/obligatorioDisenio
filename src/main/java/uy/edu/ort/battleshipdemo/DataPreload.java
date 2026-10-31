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
import uy.edu.ort.battleshipdemo.model.HuntAndAttackTactic;
import uy.edu.ort.battleshipdemo.model.InCombatStatus;
import uy.edu.ort.battleshipdemo.model.LostStatus;
import uy.edu.ort.battleshipdemo.model.PreparingStatus;
import uy.edu.ort.battleshipdemo.model.SequentialTactic;
import uy.edu.ort.battleshipdemo.model.SpiralTactic;
import uy.edu.ort.battleshipdemo.model.Tactic;
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

    private final Tactic sequential = new SequentialTactic();
    private final Tactic spiral = new SpiralTactic();
    private final Tactic huntAndAttack = new HuntAndAttackTactic();

    @Override
    public void run(String... args) throws BattleException {
        Facade facade = Facade.getInstance();

        facade.addBoatType(carrier);
        facade.addBoatType(battleship);
        facade.addBoatType(cruiser);
        facade.addBoatType(submarine);
        facade.addBoatType(destroyer);

        facade.addTactic(sequential);
        facade.addTactic(spiral);
        facade.addTactic(huntAndAttack);

        facade.addBattle(createBattle("Mar del Plata", 10, 10, new PreparingStatus(), sequential, cruiser, submarine));
        facade.addBattle(createBattle("Atlántico Sur", 12, 8, new InCombatStatus(), spiral, battleship, cruiser, destroyer));
        facade.addBattle(createBattle("Golfo de Vizcaya", 10, 10, new WonStatus(), huntAndAttack, carrier, submarine));
        facade.addBattle(createBattle("Río de la Plata", 8, 8, new WonStatus(), sequential, cruiser, submarine, destroyer));
        facade.addBattle(createBattle("Mar Caribe", 12, 12, new LostStatus(), spiral, carrier, battleship, submarine));
    }

    private Battle createBattle(String place, int width, int height, BattleStatus status, Tactic tactic,
            BoatType... boatTypes) {
        Battle battle = new Battle();
        battle.setPlace(place);
        battle.setWidth(width);
        battle.setHeight(height);
        battle.setStatus(status);
        battle.setTactic(tactic);
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
