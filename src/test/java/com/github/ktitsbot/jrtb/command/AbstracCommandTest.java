package com.github.ktitsbot.jrtb.command;

import com.github.ktitsbot.jrtb.bot.KtitsTelegramBot;
import com.github.ktitsbot.jrtb.service.SendBotMessageService;
import com.github.ktitsbot.jrtb.service.SendBotMessageServiceImpl;
import org.checkerframework.checker.units.qual.K;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public abstract class AbstracCommandTest {
    protected KtitsTelegramBot bot = Mockito.mock(KtitsTelegramBot.class);
    protected SendBotMessageService sendBotMessageService = new SendBotMessageServiceImpl(bot);

    abstract String getCommandName();
    abstract String getCommandMessage();
    abstract Command getCommand();

    @Test
    public void shouldProperlySendMessage() throws TelegramApiException {
        //given
        String chatId = "test_chat_id";
        String message = "test_message";

        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(message);
        sendMessage.setChatId(chatId);
        sendMessage.enableHtml(true);

        //when
        sendBotMessageService.sendMessage(chatId, message);

        //then
        Mockito.verify(bot).execute(sendMessage);
    }
}
