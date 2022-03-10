package com.github.ktitsbot.jrtb.bot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.generics.TelegramBot;
import org.telegram.telegrambots.starter.TelegramBotStarterConfiguration;

@Component
public class KtitsTelegramBot extends TelegramLongPollingBot {


    @Value("${bot.username}")
    private String username;

    @Value("${bot.token}")
    private String  token;

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage() && update.getMessage().hasText()){
            String message = update.getMessage().getText().trim();
            String chatId = update.getMessage().getChatId().toString();
            SendMessage sm = new SendMessage();
            sm.setChatId(chatId);
            sm.setText(message);
            try {
                execute(sm);
            }catch (TelegramApiException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return "5240809296:AAG_JDxNL67GI4xYnNWt-_agGgJnILnSQC4";
    }

}
