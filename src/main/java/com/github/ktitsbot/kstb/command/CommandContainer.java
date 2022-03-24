package com.github.ktitsbot.kstb.command;

import com.github.ktitsbot.kstb.service.LessonService;
import com.github.ktitsbot.kstb.service.SendBotMessageService;
import com.github.ktitsbot.kstb.service.TelegramUserService;
import com.google.common.collect.ImmutableMap;

public class CommandContainer {
    private final ImmutableMap<String, Command> commandMap;
    private final Command unknownCommand;

    public CommandContainer(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService, LessonService lessonService) {
        commandMap = ImmutableMap.<String, Command>builder()
                .put(CommandName.START.getCommandName(), new StartCommand(sendBotMessageService,telegramUserService))
                .put(CommandName.STOP.getCommandName(), new StopCommand(sendBotMessageService,telegramUserService))
                .put(CommandName.HELP.getCommandName(), new HelpCommand(sendBotMessageService))
                .put(CommandName.STAT.getCommandName(),new StatCommand(sendBotMessageService,telegramUserService))
                .put(CommandName.GROUP.getCommandName(),new SetGroupCommand(sendBotMessageService,telegramUserService))
                .put(CommandName.DAY.getCommandName(),new SendDayTimeTable(sendBotMessageService,lessonService,telegramUserService))
                .put(CommandName.NO.getCommandName(), new NoCommand(sendBotMessageService)).build();
        unknownCommand = new UnknownCommand(sendBotMessageService);
    }

    public Command retrieveCommand(String commandIdentifier){
        return commandMap.getOrDefault(commandIdentifier,unknownCommand);
    }
}
