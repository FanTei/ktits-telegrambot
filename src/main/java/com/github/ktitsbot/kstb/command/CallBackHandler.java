package com.github.ktitsbot.kstb.command;

import com.github.ktitsbot.kstb.repository.entity.TelegramUser;
import com.github.ktitsbot.kstb.service.SendBotMessageService;
import com.github.ktitsbot.kstb.service.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.stream.Collectors;

public class CallBackHandler {
    private final TelegramUserService userService;
    private final SendBotMessageService sendBotMessageService;

    public CallBackHandler(SendBotMessageService sendBotMessageService, TelegramUserService userService) {
        this.userService = userService;
        this.sendBotMessageService = sendBotMessageService;
    }

    public void handle(Update update) {
        Message message = new Message();
        String chatId = update.getCallbackQuery().getMessage().getChatId().toString();
        if (!userService.isActiveUser(chatId)) {
            sendBotMessageService.sendMessage(chatId, "Введите комманду  /start");
            return;
        }
        CallbackQuery callbackQuery = update.getCallbackQuery();
        if (callbackQuery.getData().toString().split(":")[0].equals("group")) {
            int groupId =        Integer.parseInt(callbackQuery.getData().toString().split(":")[1]);
            setGroupIdHandler(chatId, groupId);
        }
    }

    private void setGroupIdHandler(String chatId, int id) {
        TelegramUser user = userService.findByChatId(chatId).get();
        user.setIdGroup(id);
        userService.save(user);
    }
}
