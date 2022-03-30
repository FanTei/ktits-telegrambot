package com.github.ktitsbot.kstb.command;

import com.github.ktitsbot.kstb.service.LessonService;
import com.github.ktitsbot.kstb.service.SendBotMessageService;
import com.github.ktitsbot.kstb.service.StudentGroupService;
import com.github.ktitsbot.kstb.service.TelegramUserService;
import com.google.common.collect.ImmutableMap;

public class CommandContainer {
    private final ImmutableMap<String, Command> commandMap;
    private final Command unknownCommand;

    public CommandContainer(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService,
                            LessonService lessonService, StudentGroupService studentGroupService) {
        commandMap = ImmutableMap.<String, Command>builder()
                .put(CommandName.START.getCommandName(), new StartCommand(sendBotMessageService, telegramUserService))
                .put(CommandName.STOP.getCommandName(), new StopCommand(sendBotMessageService, telegramUserService))
                .put(CommandName.STAT.getCommandName(), new StatCommand(sendBotMessageService, telegramUserService))
                .put(CommandName.GROUP.getCommandName(), new SetCourseAndGroupCommand(sendBotMessageService, telegramUserService))
                .put(CommandName.DAY.getCommandName(), new SetDayCommand(sendBotMessageService, lessonService, telegramUserService, studentGroupService)).build();
        unknownCommand = new UnknownCommand(sendBotMessageService);
    }

    public Command retrieveCommand(String commandIdentifier) {
        return commandMap.getOrDefault(commandIdentifier, unknownCommand);
    }
}
