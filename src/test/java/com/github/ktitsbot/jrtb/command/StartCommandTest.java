package com.github.ktitsbot.jrtb.command;

import org.junit.jupiter.api.DisplayName;

@DisplayName("Unit-level testing for StartCommand")
public class StartCommandTest extends AbstracCommandTest {
    @Override
    String getCommandName() {
        return CommandName.START.getCommandName();
    }

    @Override
    String getCommandMessage() {
        return StartCommand.START_MESSAGE;
    }

    @Override
    Command getCommand() {
        return new StopCommand(sendBotMessageService);
    }
}
