package uy.edu.ort.battleshipdemo.presenter;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import uy.edu.ort.battleshipdemo.Command;
import uy.edu.ort.battleshipdemo.Commands;
import uy.edu.ort.battleshipdemo.dto.BattleDto;
import uy.edu.ort.battleshipdemo.dto.BattleHeaderDto;
import uy.edu.ort.battleshipdemo.dto.BattleRowDto;
import uy.edu.ort.battleshipdemo.model.Battle;
import uy.edu.ort.battleshipdemo.model.Facade;

@RestController
@RequestMapping("/operationsRoom")
@Scope("session")
public class OperationsRoomPresenter {

    private Facade facade;
    private List<Battle> battles;

    public OperationsRoomPresenter(Facade facade) {
        this.facade = facade;
    }

    @PostMapping("/connectedView")
    public Commands connectedView() {
        return Commands.create(wonBattles(), lostBattles(), successRate(), successDetail(), battleList());
    }

    @PostMapping("/enter")
    public Commands enter(HttpSession session, @RequestParam int posBattle) {
        Battle battle = battles.get(posBattle);
        session.setAttribute("battle", new BattleDto(battle));
        return Commands.create(selectedBattle(battle));
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
