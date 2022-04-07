package com.github.ktitsbot.kstb.handler;

import com.github.ktitsbot.kstb.repository.entity.Lesson;
import com.github.ktitsbot.kstb.repository.entity.TelegramUser;
import com.github.ktitsbot.kstb.service.LessonService;
import com.github.ktitsbot.kstb.service.SendBotMessageService;
import com.github.ktitsbot.kstb.service.TelegramUserService;

import java.text.SimpleDateFormat;
import java.util.*;

public class DayOWeekTimetableSender {
    private final TelegramUserService telegramUserService;
    private final LessonService lessonService;
    private final SendBotMessageService sendBotMessageService;

    public DayOWeekTimetableSender(TelegramUserService telegramUserService, LessonService lessonService, SendBotMessageService sendBotMessageService) {
        this.telegramUserService = telegramUserService;
        this.lessonService = lessonService;
        this.sendBotMessageService = sendBotMessageService;
    }

    public void send(String chatId, int dayOfWeekId) {
        TelegramUser user = telegramUserService.findByChatId(chatId).get();
        int groupId = user.getIdGroup();
        String messageText = lessonsToString(lessonService.findLessons(dayOfWeekId, groupId));
        sendBotMessageService.sendMessage(chatId, messageText);
    }

    private String lessonsToString(List<Lesson> lessons){
        StringBuilder stringBuilder = new StringBuilder();
        for (Lesson lesson : lessons) {
            int lessonNumber = lesson.getLessonNumber();
            String lessonName = lesson.getLessonName();
            String lessonCabinetNumber = lesson.getCabinetNumber();
            stringBuilder.append(lesson.getLessonNumber()).append(")").append(lessonName).append(" ").append(lessonCabinetNumber);
            stringBuilder.append("\n");
        }
        return "<b>" + stringBuilder.toString() + "</b>";
    }
}
