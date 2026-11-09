package uy.edu.ort.battleshipdemo.presenter;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import uy.edu.ort.battleshipdemo.Command;
import uy.edu.ort.battleshipdemo.Commands;
import uy.edu.ort.battleshipdemo.ValorDto;
import uy.edu.ort.battleshipdemo.dto.BattleDto;
import uy.edu.ort.battleshipdemo.dto.BattleHeaderDto;
import uy.edu.ort.battleshipdemo.dto.OceanSizeDto;
import uy.edu.ort.battleshipdemo.model.Battle;
import uy.edu.ort.battleshipdemo.model.BattleshipException;
import uy.edu.ort.battleshipdemo.model.Boat;
import uy.edu.ort.battleshipdemo.model.Cell;
import uy.edu.ort.battleshipdemo.model.Facade;

@RestController
@RequestMapping("/fightBattle")
@Scope("session")
public class FightBattlePresenter {

    private Facade facade;
    private Battle battle;

    public FightBattlePresenter(Facade facade) {
        this.facade = facade;
    }

    @PostMapping("/connectedView")
    public Commands connectedView(@SessionAttribute(name = "battle", required = false) BattleDto battleDto)
            throws BattleshipException {

        if (battleDto == null)
            return Commands.create(battleNotSelected());

        battle = facade.getBattle(battleDto.getPlace());

        Commands commands = Commands.create(oceanSize(), battleHeader(), casualties());
        addOwnWaters(commands);
        addEnemyWaters(commands);
        return commands;
    }

    @PostMapping("/fire")
    public Commands fire(@RequestParam int coordenadaVertical, @RequestParam int coordenadaHorizontal)
            throws BattleshipException {
        battle.fire(coordenadaVertical, coordenadaHorizontal);
        Commands commands = Commands.create(battleHeader(), casualties());
        addOwnWaters(commands);
        addEnemyWaters(commands);
        return commands;
    }

    @PostMapping("/fireAtOwnWaters")
    public Commands fireAtOwnWaters() throws BattleshipException {
        throw new BattleshipException("No puede disparar a sus propias aguas");
    }

    private Command oceanSize() {
        return new Command("oceanSize", new OceanSizeDto(battle));
    }

    private Command battleHeader() {
        return new Command("battleHeader", new BattleHeaderDto(battle));
    }

    private Command battleNotSelected() {
        return new Command("battleNotSelected", "Primero debe seleccionar una batalla");
    }

    // Formato del componente BattleShipBoard: "{5} {3}", en el orden en que se hundieron.
    private Command casualties() {
        String text = "";
        for (Boat boat : battle.getMachine().getSunkBoats()) {
            if (!text.isEmpty()) {
                text = text + " ";
            }
            text = text + "{" + boat.getSize() + "}";
        }
        return new Command("bajasEnemigas", text);
    }

    private void addOwnWaters(Commands commands) {
        for (Cell cell : battle.getHuman().getOcean().getCells()) {
            String value = cellValue(cell, true);
            if (value != null) {
                commands.add(new Command("mostrarAguasPropias",
                        new ValorDto(cell.getRow(), cell.getColumn(), value)));
            }
        }
    }

    // Los barcos enemigos no descubiertos solo se muestran si el estado de la batalla lo permite.
    private void addEnemyWaters(Commands commands) {
        for (Cell cell : battle.getMachine().getOcean().getCells()) {
            String value = cellValue(cell, battle.revealsEnemyFleet());
            if (value != null) {
                commands.add(new Command("mostrarAguasEnemigas",
                        new ValorDto(cell.getRow(), cell.getColumn(), value)));
            }
        }
    }

    private String cellValue(Cell cell, boolean showBoats) {
        if (cell.isHit()) {
            return "impacto";
        }
        if (cell.isShot()) {
            return "agua";
        }
        if (cell.hasBoat() && showBoats) {
            return "barco";
        }
        return null;
    }
}
