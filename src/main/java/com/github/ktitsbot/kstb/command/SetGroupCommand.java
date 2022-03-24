package com.github.ktitsbot.kstb.command;

import com.github.ktitsbot.kstb.command.Command;
import com.github.ktitsbot.kstb.command.StudentGroups;
import com.github.ktitsbot.kstb.service.SendBotMessageService;
import com.github.ktitsbot.kstb.service.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class SetGroupCommand implements Command {
    private final TelegramUserService telegramUserService;
    private final SendBotMessageService sendBotMessageService;
    public static final String GROUPS_MESSAGE = "Выберите группу!";

    public SetGroupCommand(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService) {
        this.telegramUserService = telegramUserService;
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        if (!telegramUserService.isActiveUser(chatId)) {
            sendBotMessageService.sendMessage(chatId, "Введите команду /start");
            return;
        }
        InlineKeyboardMarkup replyKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsList = new ArrayList<>();
        List<InlineKeyboardButton> firstRowButtonsList = new ArrayList<>();
        InlineKeyboardButton button202 = createButton("202", StudentGroups.group202.getId(), firstRowButtonsList);
        InlineKeyboardButton button203 = createButton("203", StudentGroups.group203.getId(), firstRowButtonsList);
        InlineKeyboardButton button211 = createButton("211", StudentGroups.group211.getId(), firstRowButtonsList);
        rowsList.add(firstRowButtonsList);
        List<InlineKeyboardButton> secondRowButtonsList = new ArrayList<>();
        InlineKeyboardButton button205 = createButton("205", StudentGroups.grop205.getId(), secondRowButtonsList);
        InlineKeyboardButton button215 = createButton("215", StudentGroups.group215.getId(), secondRowButtonsList);
        InlineKeyboardButton button220 = createButton("220", StudentGroups.grop220.getId(), secondRowButtonsList);
        rowsList.add(secondRowButtonsList);
        replyKeyboardMarkup.setKeyboard(rowsList);
        sendBotMessageService.sendMessage(chatId, GROUPS_MESSAGE, replyKeyboardMarkup);
    }

    private InlineKeyboardButton createButton(String text, String callBack, List<InlineKeyboardButton> buttonList) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(text);
        buttonList.add(button);
        button.setCallbackData("group:"+callBack);
        return button;
    }
}

