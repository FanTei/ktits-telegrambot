package com.github.ktitsbot.kstb.command;

public class SetCourseAndGroupCommandTest extends AbstracCommandTest{
    @Override
    String getCommandName() {
        return CommandName.GROUP.getCommandName();
    }

    @Override
    String getCommandMessage() {
        return "Курс";
    }

    @Override
    Command getCommand() {
        return new SetCourseAndGroupCommand(sendBotMessageService,telegramUserService);
    }
}
