package com.github.ktitsbot.kstb.command;

import static com.github.ktitsbot.kstb.command.StatCommand.STAT_MESSAGE;

public class StatCommandTest extends AbstracCommandTest{

    @Override
    String getCommandName() {
        return CommandName.STAT.getCommandName();
    }

    @Override
    String getCommandMessage() {
        return String.format(STAT_MESSAGE, 0);
    }

    @Override
    Command getCommand() {
        return new StatCommand(sendBotMessageService, telegramUserService);
    }
}
