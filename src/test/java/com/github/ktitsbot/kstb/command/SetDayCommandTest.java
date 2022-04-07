package com.github.ktitsbot.kstb.command;

public class SetDayCommandTest extends AbstracCommandTest{
    @Override
    String getCommandName() {
        return CommandName.DAY.getCommandName();
    }

    @Override
    String getCommandMessage() {
        return "День";
    }

    @Override
    Command getCommand() {
        return new SetDayCommand(sendBotMessageService,telegramUserService);
    }
}
