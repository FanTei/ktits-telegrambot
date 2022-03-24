package com.github.ktitsbot.kstb.bot;

import com.github.ktitsbot.kstb.command.CallBackHandler;
import com.github.ktitsbot.kstb.command.CommandContainer;
import com.github.ktitsbot.kstb.command.CommandName;
import com.github.ktitsbot.kstb.excel.ExcelApplication;
import com.github.ktitsbot.kstb.repository.LessonRepository;
import com.github.ktitsbot.kstb.repository.entity.TelegramUser;
import com.github.ktitsbot.kstb.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.stickers.Sticker;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class KtitsTelegramBot extends TelegramLongPollingBot {
    private final CallBackHandler callBackHandler;
    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    @Autowired
    public KtitsTelegramBot(TelegramUserService telegramUserService, ExcelApplication excelApplication) {

        this.telegramUserService = telegramUserService;
        this.sendBotMessageService = new SendBotMessageServiceImpl(this);
        LessonService lessonService = new LessonServiceImpl(excelApplication.getLessonRepository());
        this.commandContainer = new CommandContainer(sendBotMessageService, telegramUserService, lessonService);
        this.callBackHandler = new CallBackHandler(sendBotMessageService, telegramUserService);
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
            if (message.startsWith(COMMAND_PREFIX)) {
                String commandIdentifier = message.split(" ")[0].toLowerCase(Locale.ROOT);
                commandContainer.retrieveCommand(commandIdentifier).execute(update);
            } else {
                commandContainer.retrieveCommand(CommandName.NO.getCommandName()).execute(update);
            }
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
