package uy.edu.ort.battleshipdemo.presenter;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import jakarta.servlet.http.HttpSession;
import uy.edu.ort.battleshipdemo.Command;
import uy.edu.ort.battleshipdemo.Commands;
import uy.edu.ort.battleshipdemo.dto.BattleDto;
import uy.edu.ort.battleshipdemo.dto.BattleHeaderDto;
import uy.edu.ort.battleshipdemo.dto.BattleRowDto;
import uy.edu.ort.battleshipdemo.model.Battle;
import uy.edu.ort.battleshipdemo.model.Facade;
import uy.edu.ort.battleshipdemo.observer.Observable;
import uy.edu.ort.battleshipdemo.observer.Observer;

@RestController
@RequestMapping("/operationsRoom")
@Scope("session")
public class OperationsRoomPresenter implements Observer {

    private Facade facade;
    private BrowserConnection browserConnection;
    private List<Battle> battles;

    public OperationsRoomPresenter(Facade facade, BrowserConnection browserConnection) {
        this.facade = facade;
        this.browserConnection = browserConnection;
    }

    @GetMapping(value = "/registerSSE", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter registerSSE() {
        browserConnection.connectSSE();
        return browserConnection.getSseConnection();
    }

    @PostMapping("/connectedView")
    public Commands connectedView() {
        facade.addObserver(this);
        return operationsSummary();
    }

    @PostMapping("/disconnectedView")
    public void disconnectedView() {
        facade.removeObserver(this);
    }

    @PostMapping("/enter")
    public Commands enter(HttpSession session, @RequestParam int posBattle) {
        Battle battle = battles.get(posBattle);
        session.setAttribute("battle", new BattleDto(battle));
        return Commands.create(selectedBattle(battle));
    }

    @Override
    public void update(Observable source, Object event) {
        if (event.equals(Facade.Events.NEW_BATTLE) || event.equals(Facade.Events.BATTLE_UPDATED)) {
            browserConnection.sendJSON(operationsSummary());
        }
    }

    private Commands operationsSummary() {
        return Commands.create(wonBattles(), lostBattles(), successRate(), successDetail(), battleList());
    }

    private Command selectedBattle(Battle battle) {
        return new Command("selectedBattle", new BattleHeaderDto(battle));
    }

    private Command wonBattles() {
        return new Command("wonBattles", facade.getWonCount());
    }

    private Command lostBattles() {
        return new Command("lostBattles", facade.getLostCount());
    }

    private Command successRate() {
        return new Command("successRate", facade.getSuccessPercentage());
    }

    private Command successDetail() {
        return new Command("successDetail",
                "(" + facade.getWonCount() + " de " + facade.getBattles().size() + " batallas)");
    }

    private Command battleList() {
        battles = facade.getBattles();
        return new Command("battleList", BattleRowDto.fromList(battles));
    }
}
