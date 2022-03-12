package com.github.ktitsbot.jrtb.bot;

import com.github.ktitsbot.jrtb.command.CommandContainer;
import com.github.ktitsbot.jrtb.command.CommandName;
import com.github.ktitsbot.jrtb.service.SendBotMessageServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Locale;

@Component
public class KtitsTelegramBot extends TelegramLongPollingBot {
    public static String COMMAND_PREFIX = "/";
    private final CommandContainer commandContainer;
    @Value("${bot.username}")
    private String username;

    @Value("${bot.token}")
    private String token;

    public KtitsTelegramBot() {
        this.commandContainer = new CommandContainer(new SendBotMessageServiceImpl(this));
    }

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
