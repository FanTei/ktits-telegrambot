package com.github.ktitsbot.kstb.handler;

import com.github.ktitsbot.kstb.excel.DayOfWeek;
import com.github.ktitsbot.kstb.repository.entity.TelegramUser;
import com.github.ktitsbot.kstb.service.LessonService;
import com.github.ktitsbot.kstb.service.SendBotMessageService;
import com.github.ktitsbot.kstb.service.StudentGroupService;
import com.github.ktitsbot.kstb.service.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.*;

public class CallBackHandler {
    private final SendBotMessageService sendBotMessageService;
    private final LessonService lessonService;
    private final TelegramUserService telegramUserService;
    private final StudentGroupService studentGroupService;

    public CallBackHandler(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService, LessonService lessonService, StudentGroupService studentGroupService) {
        this.sendBotMessageService = sendBotMessageService;
        this.lessonService = lessonService;
        this.telegramUserService = telegramUserService;
        this.studentGroupService = studentGroupService;
    }

    public void handle(Update update) {
        Message message = new Message();
        String chatId = update.getCallbackQuery().getMessage().getChatId().toString();
        if (!telegramUserService.isActiveUser(chatId)) {
            sendBotMessageService.sendMessage(chatId, "Введите комманду  /start");
            return;
        }
        CallbackQuery callbackQuery = update.getCallbackQuery();
        String[] split = callbackQuery.getData().toString().split(":");
        if (split[0].equals("group")) {
            int groupId = Integer.parseInt(split[1]);
            handleGroupId(chatId, groupId);
        }
        if (split[0].equals("dayOfWeek")) {
            DayOfWeek dayOfWeek = DayOfWeek.getById(Integer.parseInt(split[1]));
            handleDayOfWeek(chatId, dayOfWeek.getId());
        }
        if (split[0].equals("course")) {
            int course = Integer.parseInt(split[1]);
            handleCourse(chatId, course);
        }
    }

    private void handleGroupId(String chatId, int groupId) {
        TelegramUser user = telegramUserService.findByChatId(chatId).get();
        user.setIdGroup(groupId);
        telegramUserService.save(user);
        sendBotMessageService.sendMessage(chatId, "Теперь выбери день.");
    }

    private void handleDayOfWeek(String chatId, int dayOfWeekId) {
        DayOWeekTimetableSender dayOWeekSender = new DayOWeekTimetableSender(telegramUserService, lessonService, sendBotMessageService);
        dayOWeekSender.send(chatId, dayOfWeekId);
    }

    private void handleCourse(String chatId, int course) {
        GroupSetter groupSetter = new GroupSetter(sendBotMessageService, telegramUserService, studentGroupService);
        groupSetter.setCourse(course);
        groupSetter.execute(chatId);
    }
}
