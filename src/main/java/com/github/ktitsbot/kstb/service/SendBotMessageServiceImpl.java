package com.github.ktitsbot.kstb.service;

import com.github.ktitsbot.kstb.bot.KtitsTelegramBot;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class SendBotMessageServiceImpl  implements SendBotMessageService{
    private final KtitsTelegramBot bot;

    @SuppressWarnings("SpringJavaAutowiredMembersInspection")
    @Autowired
    public SendBotMessageServiceImpl(KtitsTelegramBot bot) {
        this.bot = bot;
    }

    @Override
    public void sendMessage(String chatId, String message) {
        SendMessage sm = new SendMessage();
        sm.setChatId(chatId);
        sm.enableHtml(true);
        sm.setText(message);
        try {
            bot.execute(sm);
        }catch (TelegramApiException e){
            e.printStackTrace();
        }
    }
}
