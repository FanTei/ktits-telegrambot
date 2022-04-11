package com.github.ktitsbot.kstb.command;

import com.github.ktitsbot.kstb.service.SendBotMessageService;
import com.github.ktitsbot.kstb.service.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class SetCourseAndGroupCommand implements Command {
    private final String CALL_BACK_PREFIX = "course:";
    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;

    public SetCourseAndGroupCommand(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        if (!telegramUserService.isActiveUser(chatId)) {
            sendBotMessageService.sendMessage(chatId, "Введите команду /start");
            return;
        }
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(createKeyboard());
        sendBotMessageService.sendMessage(chatId, "Группа", inlineKeyboardMarkup);
    }

    private List<List<InlineKeyboardButton>> createKeyboard() {
        List<List<InlineKeyboardButton>> rowsList = new ArrayList<>();
        List<InlineKeyboardButton> buttonsRow = new ArrayList<>();
        for (int c = 1; c <= 4; c++) {
            InlineKeyboardButton button = createButton("Курс " + c, Integer.toString(c));
            buttonsRow.add(button);
        }
        rowsList.add(buttonsRow);
        return rowsList;
    }

    private InlineKeyboardButton createButton(String text, String callBack) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(text);
        button.setCallbackData(CALL_BACK_PREFIX + callBack);
        return button;
    }
}
