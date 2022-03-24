package com.github.ktitsbot.kstb.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

public interface SendBotMessageService {
    void sendMessage(String chatId,String message);

    void sendMessage(String chatId,String message,ReplyKeyboard replyKeyboardMarkup);
}
