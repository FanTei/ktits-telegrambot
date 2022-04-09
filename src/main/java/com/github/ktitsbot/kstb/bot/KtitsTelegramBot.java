package com.github.ktitsbot.kstb.bot;

import com.github.ktitsbot.kstb.handler.CallBackHandler;
import com.github.ktitsbot.kstb.command.CommandContainer;
import com.github.ktitsbot.kstb.command.CommandName;
import com.github.ktitsbot.kstb.excel.DayOfWeek;
import com.github.ktitsbot.kstb.excel.ExcelApplication;
import com.github.ktitsbot.kstb.repository.entity.Lesson;
import com.github.ktitsbot.kstb.repository.entity.TelegramUser;
import com.github.ktitsbot.kstb.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

@Component
public class KtitsTelegramBot extends TelegramLongPollingBot {
    private final CallBackHandler callBackHandler;
    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;
    private final LessonService lessonService;
    private final StudentGroupService studentGroupService;

    @Autowired
    public KtitsTelegramBot(TelegramUserService telegramUserService, ExcelApplication excelApplication) {

        studentGroupService = new StudentGroupServiceImpl(excelApplication.getStudentGroupRepository());
        this.telegramUserService = telegramUserService;
        this.sendBotMessageService = new SendBotMessageServiceImpl(this);
        lessonService = new LessonServiceImpl(excelApplication.getLessonRepository());
        this.commandContainer = new CommandContainer(sendBotMessageService, telegramUserService, lessonService, studentGroupService);
        this.callBackHandler = new CallBackHandler(sendBotMessageService, telegramUserService, lessonService, studentGroupService);

    }

    public static String COMMAND_PREFIX = "/";
    private final CommandContainer commandContainer;
    @Value("${bot.username}")
    private String username;

    @Value("${bot.token}")
    private String token;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText().trim();
            String chatId = update.getMessage().getChatId().toString();
            commandContainer.retrieveCommand(message).execute(update);
        } else if (update.hasCallbackQuery()) {
            callBackHandler.handle(update);
        }
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }

}
