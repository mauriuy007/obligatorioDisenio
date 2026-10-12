package com.example.battleship.presenter.demo;

import static com.example.battleship.presenter.command.BoardProtocol.COLUMN_PARAM;
import static com.example.battleship.presenter.command.BoardProtocol.ENEMY_CASUALTIES;
import static com.example.battleship.presenter.command.BoardProtocol.INIT;
import static com.example.battleship.presenter.command.BoardProtocol.ROW_PARAM;
import static com.example.battleship.presenter.command.BoardProtocol.SHIP;
import static com.example.battleship.presenter.command.BoardProtocol.SHOW_ENEMY_WATERS;
import static com.example.battleship.presenter.command.BoardProtocol.SHOW_OWN_WATERS;
import static com.example.battleship.presenter.command.BoardProtocol.WATER;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.battleship.presenter.command.Command;
import com.example.battleship.presenter.command.Commands;
import com.example.battleship.presenter.dto.BoardInitDto;
import com.example.battleship.presenter.dto.CellDto;

/**
 * Course demo showing how to integrate BattleShipBoard.js with a presenter.
 * View: /board-demo.html
 */
@RestController
@RequestMapping("/demo")
public class BoardDemoPresenter {

    private static final String SHOW_MESSAGE = "showMessage";

    @PostMapping("/init")
    public Commands init() {
        Command hello = new Command(SHOW_MESSAGE, "hola mundo!");
        Command init = new Command(INIT, new BoardInitDto(10, 10, "/demo/place-ship", "/demo/fire"));
        Command casualties = new Command(ENEMY_CASUALTIES, "{5},{3},{3}");
        return Commands.create(hello, init, casualties);
    }

    @PostMapping("/action1")
    public Commands action1() {
        Command action = new Command(SHOW_MESSAGE, "Accion 1 ejecutada!");
        return Commands.create(action);
    }

    @PostMapping("/action2")
    public Commands action2() {
        Command action = new Command(SHOW_MESSAGE, "Accion 2 ejecutada!");
        return Commands.create(action);
    }

    @PostMapping("/action3")
    public Commands action3() {
        Command action = new Command(SHOW_MESSAGE, "Accion 3 ejecutada!");
        return Commands.create(action);
    }

    @PostMapping("/action4")
    public Commands action4() {
        Command action = new Command(SHOW_MESSAGE, "Accion 4 ejecutada!");
        return Commands.create(action);
    }

    @PostMapping("/place-ship")
    public Commands placeShip(@RequestParam(ROW_PARAM) int row, @RequestParam(COLUMN_PARAM) int column) {
        System.out.println("Place ship at " + row + " and " + column);
        Command ownShip = new Command(SHOW_OWN_WATERS, new CellDto(row, column, SHIP));
        return Commands.create(ownShip);
    }

    @PostMapping("/fire")
    public Commands fire(@RequestParam(ROW_PARAM) int row, @RequestParam(COLUMN_PARAM) int column) {
        System.out.println("Fire at " + row + " and " + column);
        Command water = new Command(SHOW_ENEMY_WATERS, new CellDto(row, column, WATER));
        return Commands.create(water);
    }
}
