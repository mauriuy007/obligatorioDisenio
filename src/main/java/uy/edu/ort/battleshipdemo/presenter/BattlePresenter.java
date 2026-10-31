package uy.edu.ort.battleshipdemo.presenter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uy.edu.ort.battleshipdemo.Command;
import uy.edu.ort.battleshipdemo.Commands;
import uy.edu.ort.battleshipdemo.model.Battle;
import uy.edu.ort.battleshipdemo.model.Facade;

@RestController
@RequestMapping("/battle")
public class BattlePresenter {

    private final Facade facade = Facade.getInstance();

    @PostMapping("/connectedView")
    public Commands connectedView() {
        return Commands.create(wonBattles(), lostBattles(), successRate(), battleList());
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

    private Command battleList() {
        List<BattleRowDto> rows = new ArrayList<>();
        for (Battle battle : facade.getBattles()) {
            rows.add(new BattleRowDto(battle));
        }
        return new Command("battleList", rows);
    }
}
