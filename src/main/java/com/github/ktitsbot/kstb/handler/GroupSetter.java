package com.github.ktitsbot.kstb.handler;

import com.github.ktitsbot.kstb.repository.entity.StudentGroup;
import com.github.ktitsbot.kstb.service.SendBotMessageService;
import com.github.ktitsbot.kstb.service.StudentGroupService;
import com.github.ktitsbot.kstb.service.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class GroupSetter {
    private final TelegramUserService telegramUserService;
    private final SendBotMessageService sendBotMessageService;
    private final StudentGroupService studentGroupService;
    private final String CALL_BACK_PREFIX = "group:";
    public static final String GROUPS_MESSAGE = "Выберите группу!";
    private int course = -1;

    public GroupSetter(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService, StudentGroupService studentGroupService) {
        this.telegramUserService = telegramUserService;
        this.sendBotMessageService = sendBotMessageService;
        this.studentGroupService = studentGroupService;
    }

    public void setCourse(int course) {
        this.course = course;
    }

    public void execute(String chatId) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(createKeyboard());
        sendBotMessageService.sendMessage(chatId, GROUPS_MESSAGE, inlineKeyboardMarkup);
    }

    private List<List<InlineKeyboardButton>> createKeyboard() {
        List<StudentGroup> studentGroups = studentGroupService.getAllStudentGroups();
        List<List<InlineKeyboardButton>> rowsList = new ArrayList<>();
        List<InlineKeyboardButton> buttonsRow = new ArrayList<>();
        for (StudentGroup group : studentGroups) {
            if (group.getCourse() == course) {
                InlineKeyboardButton button = createButton(Integer.toString(group.getGroupNumber()),
                        Integer.toString(group.getGroupId()));
                if (buttonsRow.size() == 3) {
                    rowsList.add(buttonsRow);
                    buttonsRow = new ArrayList<>();
                }
                buttonsRow.add(button);
            }
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

