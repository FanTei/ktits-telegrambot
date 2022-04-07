package com.github.ktitsbot.kstb.command;

import com.github.ktitsbot.kstb.excel.DayOfWeek;
import com.github.ktitsbot.kstb.service.LessonService;
import com.github.ktitsbot.kstb.service.SendBotMessageService;
import com.github.ktitsbot.kstb.service.StudentGroupService;
import com.github.ktitsbot.kstb.service.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SetDayCommand implements Command {
    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;
    private final String CALL_BACK_PREFIX = "dayOfWeek:";

    public SetDayCommand(SendBotMessageService sendBotMessageService,TelegramUserService telegramUserService) {
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
        if (telegramUserService.findByChatId(chatId).get().getIdGroup() == -1) {
            sendBotMessageService.sendMessage(chatId, "Сначала выберите группу");
            return;
        }
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(createKeyboard());
        sendBotMessageService.sendMessage(chatId, "Test", inlineKeyboardMarkup);
    }

    private InlineKeyboardButton createButton(String text, String callBack) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(text);
        button.setCallbackData(CALL_BACK_PREFIX + callBack);
        return button;
    }

    private List<List<InlineKeyboardButton>> createKeyboard() {
        List<DayOfWeek> daysOfWeek = Arrays.asList(DayOfWeek.values());
        List<List<InlineKeyboardButton>> rowsList = new ArrayList<>();
        List<InlineKeyboardButton> buttonsRow = new ArrayList<>();
        for (DayOfWeek dayOfWeek : daysOfWeek) {
            InlineKeyboardButton button = createButton(dayOfWeek.toString(), Integer.toString(dayOfWeek.getId()));
            if (buttonsRow.size() == 3) {
                rowsList.add(buttonsRow);
                buttonsRow = new ArrayList<>();
            }
            buttonsRow.add(button);
        }
        rowsList.add(buttonsRow);
        return rowsList;
    }
}

