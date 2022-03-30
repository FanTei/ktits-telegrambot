package com.github.ktitsbot.kstb.command;

import com.github.ktitsbot.kstb.excel.ExcelApplication;
import com.github.ktitsbot.kstb.service.LessonServiceImpl;
import com.github.ktitsbot.kstb.service.SendBotMessageService;
import com.github.ktitsbot.kstb.service.StudentGroupServiceImpl;
import com.github.ktitsbot.kstb.service.TelegramUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;

@DisplayName("Unit-level testing for CommandContainer")
public class CommandContainerTest {

    private CommandContainer commandContainer;

    @BeforeEach
    public void init() {
        SendBotMessageService sendBotMessageService = Mockito.mock(SendBotMessageService.class);
        TelegramUserService telegramUserService = Mockito.mock(TelegramUserService.class);
        ExcelApplication excelApplication = Mockito.mock(ExcelApplication.class);
        commandContainer = new CommandContainer(sendBotMessageService,telegramUserService,new LessonServiceImpl(excelApplication.getLessonRepository()),new StudentGroupServiceImpl(excelApplication.getStudentGroupRepository()));
    }

    @Test
    private void shouldGetAllTheExistingCommands() {
        Arrays.stream(CommandName.values()).forEach(commandName -> {
            Command command = commandContainer.retrieveCommand(commandName.getCommandName());
            Assertions.assertNotEquals(UnknownCommand.class, command.getClass());
        });
    }

    @Test
    public void shouldReturnUnknownCommand() {
        String unknownCommand = "/fgjhdfgdfg";

        Command command = commandContainer.retrieveCommand(unknownCommand);
        Assertions.assertEquals(UnknownCommand.class, command.getClass());

    }
}
