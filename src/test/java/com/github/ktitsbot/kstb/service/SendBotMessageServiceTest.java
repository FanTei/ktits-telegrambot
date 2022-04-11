package com.github.ktitsbot.kstb.service;

import com.github.ktitsbot.kstb.bot.KtitsTelegramBot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@DisplayName("Unit-level testing for SendBotMessageService")
public class SendBotMessageServiceTest {
    private SendBotMessageService sendBotMessageService;
    private KtitsTelegramBot bot;

    @BeforeEach
    public void init(){
        bot = Mockito.mock(KtitsTelegramBot.class);
        sendBotMessageService = new SendBotMessageServiceImpl(bot);
    }
    @Test
    public void shouldProperlySendMessage() throws TelegramApiException{
        String chatId = "test_chat_id";
        String  message = "test_message";
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(message);
        sendMessage.setChatId(chatId);
        sendMessage.enableHtml(true);
        sendBotMessageService.sendMessage(chatId,message);
        Mockito.verify(bot).execute(sendMessage);
    }
}
