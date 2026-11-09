package uy.edu.ort.battleshipdemo.conf;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import uy.edu.ort.battleshipdemo.model.Battle;
import uy.edu.ort.battleshipdemo.model.BattleshipException;
import uy.edu.ort.battleshipdemo.model.Boat;
import uy.edu.ort.battleshipdemo.model.BoatType;
import uy.edu.ort.battleshipdemo.model.Cell;
import uy.edu.ort.battleshipdemo.model.Facade;
import uy.edu.ort.battleshipdemo.model.Player;
import uy.edu.ort.battleshipdemo.model.TacticType;

// Datos de prueba: las batallas se crean con facade.createBattle (igual que en el CU02) y se juegan
// con el modelo, así cada una queda en un estado consistente.
@Component
public class DataPreload implements CommandLineRunner {

    private Facade facade = Facade.getInstance();
    private Random random = new Random();

    private TacticType sequential = new TacticType("Secuencial");
    private TacticType spiral = new TacticType("Espiral");
    private TacticType huntAndAttack = new TacticType("Caza y ataque");

    @Override
    public void run(String... args) {
        try {
            facade.addBoatType(new BoatType("Portaaviones", 5));
            facade.addBoatType(new BoatType("Acorazado", 4));
            facade.addBoatType(new BoatType("Crucero", 3));
            facade.addBoatType(new BoatType("Submarino", 2));
            facade.addBoatType(new BoatType("Destructor", 1));

            facade.addTacticType(sequential);
            facade.addTacticType(spiral);
            facade.addTacticType(huntAndAttack);

            // En preparación: solo la flota enemiga está ubicada.
            createBattle("Mar del Plata", 10, 10, sequential, quantities(0, 0, 1, 1, 0));

            preloadInCombatBattle();
            preloadWonBattle(createBattle("Golfo de Vizcaya", 10, 10, huntAndAttack, quantities(1, 0, 0, 1, 0)));
            preloadWonBattle(createBattle("Río de la Plata", 8, 8, sequential, quantities(0, 0, 1, 1, 1)));
            preloadLostBattle();
        } catch (BattleshipException e) {
            System.out.println("Error al cargar datos de prueba " + e.getMessage());
        }
    }

    // En combate: tres rondas jugadas.
    private void preloadInCombatBattle() throws BattleshipException {
        Battle battle = createBattle("Atlántico Sur", 12, 8, spiral, quantities(0, 1, 1, 0, 1));
        battle.getHuman().deployRandomly(random);
        battle.startCombat();
        fireAt(battle, true);
        fireAt(battle, false);
        fireAt(battle, false);
    }

    // Ganada: el humano dispara solo a barcos enemigos, así hunde la flota antes que la máquina.
    private void preloadWonBattle(Battle battle) throws BattleshipException {
        battle.getHuman().deployRandomly(random);
        battle.startCombat();
        while (!battle.isWon()) {
            fireAt(battle, true);
        }
    }

    // Perdida: la flota humana queda en el centro, donde empieza la espiral, y el humano solo dispara al agua.
    private void preloadLostBattle() throws BattleshipException {
        Battle battle = createBattle("Mar Caribe", 12, 12, spiral, quantities(1, 1, 0, 1, 0));
        Player human = battle.getHuman();
        List<Boat> boats = human.getFleet().getBoats();
        human.placeBoat(boats.get(0), 6, 3, true);
        human.placeBoat(boats.get(1), 8, 3, true);
        human.placeBoat(boats.get(2), 4, 4, true);
        battle.startCombat();
        while (!battle.isLost()) {
            fireAt(battle, false);
        }
    }

    private Battle createBattle(String place, int width, int height, TacticType tacticType,
            List<Integer> quantities) throws BattleshipException {
        return facade.createBattle(place, tacticType, width, height, quantities);
    }

    private List<Integer> quantities(int carriers, int battleships, int cruisers, int submarines, int destroyers) {
        List<Integer> quantities = new ArrayList<>();
        quantities.add(carriers);
        quantities.add(battleships);
        quantities.add(cruisers);
        quantities.add(submarines);
        quantities.add(destroyers);
        return quantities;
    }

    private void fireAt(Battle battle, boolean boat) throws BattleshipException {
        for (Cell cell : battle.getMachine().getOcean().getUnshotCells()) {
            if (cell.hasBoat() == boat) {
                battle.fire(cell.getRow(), cell.getColumn());
                return;
            }
        }
        throw new BattleshipException("Precarga: no quedan celdas para disparar en " + battle.getPlace());
    }
}
