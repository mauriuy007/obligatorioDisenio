package com.example.battleship.presenter.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Getter;

public class Commands {

    @Getter
    private List<Command> commands;

    private Commands(List<Command> commands) {
        this.commands = new ArrayList<>(commands);
    }

    /**
     * Creates a Commands with zero or more initial commands
     * @param commands Initial commands (may be empty)
     * @return New Commands instance
     */
    public static Commands create(Command... commands) {
        return new Commands(Arrays.asList(commands));
    }

    /**
     * Adds a command to the set
     * @param command Command to add
     * @return This instance (fluent API)
     */
    public Commands add(Command command) {
        this.commands.add(command);
        return this;
    }

    /**
     * Adds multiple commands to the set
     * @param commands Commands to add
     * @return This instance (fluent API)
     */
    public Commands add(Command... commands) {
        this.commands.addAll(Arrays.asList(commands));
        return this;
    }
}
