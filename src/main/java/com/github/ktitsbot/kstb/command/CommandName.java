package com.github.ktitsbot.kstb.command;

public enum CommandName {
    START("/start"),
    STOP("/stop"),
    NO("/no"),
    STAT("/stat"),
    GROUP("\uD83C\uDD95Выбор группы"),
    DAY("\uD83D\uDCC5Выбор дня");
    private final String commandName;

    CommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName(){
        return commandName;
    }
}
