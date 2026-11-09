package uy.edu.ort.battleshipdemo.presenter;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import uy.edu.ort.battleshipdemo.Command;
import uy.edu.ort.battleshipdemo.Commands;
import uy.edu.ort.battleshipdemo.dto.BattleDto;
import uy.edu.ort.battleshipdemo.dto.BattleHeaderDto;
import uy.edu.ort.battleshipdemo.model.Battle;
import uy.edu.ort.battleshipdemo.model.BattleshipException;
import uy.edu.ort.battleshipdemo.model.Facade;

@RestController
@RequestMapping("/deployFleet")
@Scope("session")
public class DeployFleetPresenter {

    private Facade facade;
    private Battle battle;

    public DeployFleetPresenter(Facade facade) {
        this.facade = facade;
    }

    @PostMapping("/connectedView")
    public Commands connectedView(@SessionAttribute(name = "battle", required = false) BattleDto battleDto)
            throws BattleshipException {

        if (battleDto == null)
            return Commands.create(battleNotSelected());

        battle = facade.getBattle(battleDto.getPlace());

        return Commands.create(battleHeader());
    }

    private Command battleHeader() {
        return new Command("battleHeader", new BattleHeaderDto(battle));
    }

    private Command battleNotSelected() {
        return new Command("battleNotSelected", "Primero debe seleccionar una batalla");
    }
}
