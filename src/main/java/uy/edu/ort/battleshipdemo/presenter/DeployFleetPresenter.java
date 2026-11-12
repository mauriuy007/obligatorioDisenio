package uy.edu.ort.battleshipdemo.presenter;

import java.util.List;

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
import uy.edu.ort.battleshipdemo.dto.BoatTypeDto;
import uy.edu.ort.battleshipdemo.dto.OceanCellsDto;
import uy.edu.ort.battleshipdemo.dto.OceanSizeDto;
import uy.edu.ort.battleshipdemo.model.Battle;
import uy.edu.ort.battleshipdemo.model.BattleshipException;
import uy.edu.ort.battleshipdemo.model.Boat;
import uy.edu.ort.battleshipdemo.model.Facade;
import uy.edu.ort.battleshipdemo.observer.Observable;
import uy.edu.ort.battleshipdemo.observer.Observer;

@RestController
@RequestMapping("/deployFleet")
@Scope("session")
public class DeployFleetPresenter implements Observer {

    private Facade facade;
    private BrowserConnection browserConnection;
    private Battle battle;
    private boolean horizontal = true;

    public DeployFleetPresenter(Facade facade, BrowserConnection browserConnection) {
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
        horizontal = true;
        return deployState(Commands.create(oceanSize(), orientation()));
    }

    @PostMapping("/disconnectedView")
    public void disconnectedView() {
        if (battle != null) {
            battle.removeObserver(this);
        }
    }

    @PostMapping("/placeBoat")
    public Commands placeBoat(@RequestParam int coordenadaVertical, @RequestParam int coordenadaHorizontal)
            throws BattleshipException {
        battle.placeBoat(coordenadaVertical, coordenadaHorizontal, horizontal);
        return deployState(Commands.create());
    }

    @PostMapping("/enemyWaters")
    public Commands enemyWaters() throws BattleshipException {
        throw new BattleshipException("No puede ubicar barcos en aguas enemigas");
    }

    @PostMapping("/changeOrientation")
    public Commands changeOrientation(@RequestParam boolean horizontal) {
        this.horizontal = horizontal;
        return Commands.create(orientation(), actionLegend());
    }

    @PostMapping("/startCombat")
    public Commands startCombat() throws BattleshipException {
        battle.startCombat();
        return Commands.create(combatStarted());
    }

    @Override
    public void update(Observable source, Object event) {
        if (event.equals(Battle.Events.BOAT_PLACED)) {
            browserConnection.sendJSON(deployState(Commands.create()));
        }
        if (event.equals(Battle.Events.COMBAT_STARTED)) {
            browserConnection.sendJSON(Commands.create(combatStarted()));
        }
    }

    private void observe(Battle selected) {
        if (battle != null) {
            battle.removeObserver(this);
        }
        battle = selected;
        battle.addObserver(this);
    }

    private Commands deployState(Commands commands) {
        commands.add(battleHeader(), availableBoats(), actionLegend());
        for (ValorDto value : OceanCellsDto.fromOcean(battle.getHuman().getOcean(), true)) {
            commands.add(new Command("mostrarAguasPropias", value));
        }
        return commands;
    }

    private Command oceanSize() {
        return new Command("oceanSize", new OceanSizeDto(battle));
    }

    private Command battleHeader() {
        return new Command("battleHeader", new BattleHeaderDto(battle));
    }

    private Command availableBoats() {
        return new Command("availableBoats", BoatTypeDto.fromBoats(battle.getHuman().getFleet().getUnplacedBoats()));
    }

    private Command orientation() {
        return new Command("orientation", horizontal);
    }

    private Command actionLegend() {
        List<Boat> unplaced = battle.getHuman().getFleet().getUnplacedBoats();
        if (unplaced.isEmpty()) {
            return new Command("actionLegend", "Batalla preparada");
        }
        String orientationName = horizontal ? "Horizontal" : "Vertical";
        return new Command("actionLegend",
                "Posicione su " + unplaced.get(0).getType().getName() + " en forma " + orientationName);
    }

    private Command combatStarted() {
        return new Command("combatStarted", new BattleHeaderDto(battle));
    }

    private Command battleNotSelected() {
        return new Command("battleNotSelected", "Primero debe seleccionar una batalla");
    }
}
