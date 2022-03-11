package com.github.ktitsbot.jrtb.command;

import org.junit.jupiter.api.DisplayName;

@DisplayName("Unit-level testing for HelpCommand")
public class HelpCommandTest extends AbstracCommandTest{
    @Override
    String getCommandName() {
        return CommandName.HELP.getCommandName();
    }

    @Override
    String getCommandMessage() {
        return HelpCommand.HELP_MESSAGE;
    }

    @Override
    Command getCommand() {
        return new HelpCommand(sendBotMessageService);
    }
}
