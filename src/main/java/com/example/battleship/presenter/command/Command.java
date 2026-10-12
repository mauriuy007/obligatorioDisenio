package com.example.battleship.presenter.command;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

/**
 * A single command sent to the frontend.
 * Each command has an identifier (id) and a parameter (associated data).
 * The JSON keys are fixed by CommandDispatcher.js.
 */
public class Command {
    @Getter
    private String id;
    @Getter
    @JsonProperty("parametro")
    private Object parameter;

    public Command(String id, Object parameter) {
        this.id = id;
        this.parameter = parameter;
    }

    public Command() {
    }
}
