package uy.edu.ort.battleshipdemo.presenter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uy.edu.ort.battleshipdemo.Command;
import uy.edu.ort.battleshipdemo.Commands;
import uy.edu.ort.battleshipdemo.dto.BoatTypeDto;
import uy.edu.ort.battleshipdemo.dto.PrepareBattleDto;
import uy.edu.ort.battleshipdemo.exception.InvalidActionException;
import uy.edu.ort.battleshipdemo.model.BoatType;
import uy.edu.ort.battleshipdemo.model.Facade;
import uy.edu.ort.battleshipdemo.model.Tactic;

@RestController
@RequestMapping("/battle")
public class PrepareBattlePresenter {

    private final Facade facade = Facade.getInstance();

    @PostMapping("/prepareBattleView")
    public Commands prepareBattleView() {
        return Commands.create(tactics(), boatTypes());
    }

    @PostMapping("/prepareBattle")
    public Commands prepareBattle(PrepareBattleDto form) throws InvalidActionException {
        facade.createBattle(form.getPlace(), form.getTactic(), valueOrZero(form.getWidth()),
                valueOrZero(form.getHeight()), form.getQuantities());
        return Commands.create(new Command("goTo", "/sala-operaciones.html"));
    }

    private int valueOrZero(Integer value) {
        return value == null ? 0 : value;
    }

    private Command tactics() {
        List<String> names = new ArrayList<>();
        for (Tactic tactic : facade.getTactics()) {
            names.add(tactic.getName());
        }
        return new Command("tactics", names);
    }

    private Command boatTypes() {
        List<BoatTypeDto> rows = new ArrayList<>();
        for (BoatType boatType : facade.getBoatTypes()) {
            rows.add(new BoatTypeDto(boatType));
        }
        return new Command("boatTypes", rows);
    }
}
