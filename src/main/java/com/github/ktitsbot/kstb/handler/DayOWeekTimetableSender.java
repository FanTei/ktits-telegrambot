package com.github.ktitsbot.kstb.handler;

import com.github.ktitsbot.kstb.repository.entity.TelegramUser;
import com.github.ktitsbot.kstb.service.LessonService;
import com.github.ktitsbot.kstb.service.SendBotMessageService;
import com.github.ktitsbot.kstb.service.TelegramUserService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;

public class DayOWeekTimetableSender {
    private final TelegramUserService telegramUserService;
    private final LessonService lessonService;
    private final SendBotMessageService sendBotMessageService;
    private final Timer timer = new Timer();

    public DayOWeekTimetableSender(TelegramUserService telegramUserService, LessonService lessonService, SendBotMessageService sendBotMessageService) {
        this.telegramUserService = telegramUserService;
        this.lessonService = lessonService;
        this.sendBotMessageService = sendBotMessageService;
    }

    public void send(String chatId, int dayOfWeekId) {
        TelegramUser user = telegramUserService.findByChatId(chatId).get();
        int groupId = user.getIdGroup();
        String messageText = lessonService.lessonsToString(lessonService.findLessons(dayOfWeekId, groupId));
        sendBotMessageService.sendMessage(chatId, messageText);
    }

    private void setTimeToSendEveryDay(String chatId) {
        timer.purge();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MILLISECOND, getTimeToSendTimeTableInMilliSeconds());
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                DayOWeekTimetableSender sender = new DayOWeekTimetableSender(telegramUserService, lessonService, sendBotMessageService);
                sender.send(chatId, calendar.getFirstDayOfWeek());
            }
        };
        System.out.println(calendar);
        timer.schedule(task, calendar.getTime());
    }

    private int getTimeToSendTimeTableInMilliSeconds() {
        Calendar calendar = GregorianCalendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH-m-s");
        String time = simpleDateFormat.format(calendar.getTime());
        String[] split = time.split("-");
        long milliseconds = Integer.parseInt(split[0]) * 3600000L + Integer.parseInt(split[1]) * 60000L + Integer.parseInt(split[2]) * 100L;// 24 - текущее время + 6;
        return (int) ((30 * 3600000) - milliseconds);
    }
}
