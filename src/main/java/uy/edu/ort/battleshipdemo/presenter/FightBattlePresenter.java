package uy.edu.ort.battleshipdemo.presenter;

import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import uy.edu.ort.battleshipdemo.Command;
import uy.edu.ort.battleshipdemo.Commands;
import uy.edu.ort.battleshipdemo.ValorDto;
import uy.edu.ort.battleshipdemo.dto.BattleDto;
import uy.edu.ort.battleshipdemo.dto.BattleHeaderDto;
import uy.edu.ort.battleshipdemo.dto.OceanCellsDto;
import uy.edu.ort.battleshipdemo.dto.OceanSizeDto;
import uy.edu.ort.battleshipdemo.model.Battle;
import uy.edu.ort.battleshipdemo.model.BattleshipException;
import uy.edu.ort.battleshipdemo.model.Boat;
import uy.edu.ort.battleshipdemo.model.Facade;
import uy.edu.ort.battleshipdemo.observer.Observable;
import uy.edu.ort.battleshipdemo.observer.Observer;

@RestController
@RequestMapping("/fightBattle")
@Scope("session")
public class FightBattlePresenter implements Observer {

    private Facade facade;
    private BrowserConnection browserConnection;
    private Battle battle;

    public FightBattlePresenter(Facade facade, BrowserConnection browserConnection) {
        this.facade = facade;
        this.browserConnection = browserConnection;
    }

    @GetMapping(value = "/registerSSE", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter registerSSE() {
        browserConnection.connectSSE();
        return browserConnection.getSseConnection();
    }

    @PostMapping("/connectedView")
    public Commands connectedView(@SessionAttribute(name = "battle", required = false) BattleDto battleDto)
            throws BattleshipException {

        if (battleDto == null)
            return Commands.create(battleNotSelected());

        observe(facade.getBattle(battleDto.getPlace()));
        return battleState(Commands.create(oceanSize()));
    }

    @PostMapping("/disconnectedView")
    public void disconnectedView() {
        if (battle != null) {
            battle.removeObserver(this);
        }
    }

    @PostMapping("/fire")
    public Commands fire(@RequestParam int coordenadaVertical, @RequestParam int coordenadaHorizontal)
            throws BattleshipException {
        battle.fire(coordenadaVertical, coordenadaHorizontal);
        return battleState(Commands.create());
    }

    @PostMapping("/fireAtOwnWaters")
    public Commands fireAtOwnWaters() throws BattleshipException {
        throw new BattleshipException("No puede disparar a sus propias aguas");
    }

    @Override
    public void update(Observable source, Object event) {
        if (event.equals(Battle.Events.ROUND_PLAYED) || event.equals(Battle.Events.COMBAT_STARTED)) {
            browserConnection.sendJSON(battleState(Commands.create()));
        }
    }

    private void observe(Battle selected) {
        if (battle != null) {
            battle.removeObserver(this);
        }
        battle = selected;
        battle.addObserver(this);
    }

    private Commands battleState(Commands commands) {
        commands.add(battleHeader(), casualties());
        addOwnWaters(commands);
        addEnemyWaters(commands);
        return commands;
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
        for (ValorDto value : OceanCellsDto.fromOcean(battle.getHuman().getOcean(), true)) {
            commands.add(new Command("mostrarAguasPropias", value));
        }
    }

    private void addEnemyWaters(Commands commands) {
        for (ValorDto value : OceanCellsDto.fromOcean(battle.getMachine().getOcean(), battle.revealsEnemyFleet())) {
            commands.add(new Command("mostrarAguasEnemigas", value));
        }
    }
}
