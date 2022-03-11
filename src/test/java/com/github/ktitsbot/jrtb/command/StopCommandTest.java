package com.github.ktitsbot.jrtb.command;

import org.junit.jupiter.api.DisplayName;

@DisplayName("Unit-level testing for StopCommand")
public class StopCommandTest extends AbstracCommandTest{
    @Override
    String getCommandName() {
        return CommandName.STOP.getCommandName();
    }

    @Override
    String getCommandMessage() {
        return StopCommand.STOP_MESSAGE;
    }

    @Override
    Command getCommand() {
        return new StopCommand(sendBotMessageService);
    }
}
