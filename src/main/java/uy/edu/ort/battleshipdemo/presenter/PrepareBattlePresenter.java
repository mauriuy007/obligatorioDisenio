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
import uy.edu.ort.battleshipdemo.dto.BoatTypeDto;
import uy.edu.ort.battleshipdemo.model.Battle;
import uy.edu.ort.battleshipdemo.model.BattleshipException;
import uy.edu.ort.battleshipdemo.model.Facade;
import uy.edu.ort.battleshipdemo.model.TacticType;

@RestController
@RequestMapping("/prepareBattle")
@Scope("session")
public class PrepareBattlePresenter {

    private Facade facade;
    private List<TacticType> tacticTypes;

    public PrepareBattlePresenter(Facade facade) {
        this.facade = facade;
    }

    @PostMapping("/connectedView")
    public Commands connectedView() {
        return Commands.create(tacticTypes(), boatTypes());
    }

    // quantities trae la cantidad de cada tipo de barco, en el mismo orden en que se enviaron a la vista.
    @PostMapping("/continue")
    public Commands continuePreparation(HttpSession session, @RequestParam String place,
            @RequestParam int posTactic, @RequestParam int width, @RequestParam int height,
            @RequestParam List<Integer> quantities) throws BattleshipException {
        TacticType tacticType = tacticTypes.get(posTactic);
        Battle battle = facade.createBattle(place, tacticType, width, height, quantities);
        session.setAttribute("battle", new BattleDto(battle));
        return Commands.create(preparedBattle(battle));
    }

    private Command preparedBattle(Battle battle) {
        return new Command("preparedBattle", new BattleHeaderDto(battle));
    }

    private Command tacticTypes() {
        tacticTypes = facade.getTacticTypes();
        return new Command("tacticTypes", tacticTypes);
    }

    private Command boatTypes() {
        return new Command("boatTypes", BoatTypeDto.fromList(facade.getBoatTypes()));
    }
}
