package com.github.ktitsbot.kstb.command;

import com.github.ktitsbot.kstb.repository.entity.Lesson;
import com.github.ktitsbot.kstb.repository.entity.TelegramUser;
import com.github.ktitsbot.kstb.service.LessonService;
import com.github.ktitsbot.kstb.service.SendBotMessageService;
import com.github.ktitsbot.kstb.service.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public class SendDayTimeTable implements Command {
    private final SendBotMessageService sendBotMessageService;
    private final LessonService lessonService;
    private final TelegramUserService telegramUserService;

    public SendDayTimeTable(SendBotMessageService sendBotMessageService, LessonService lessonService, TelegramUserService telegramUserService) {
        this.sendBotMessageService = sendBotMessageService;
        this.lessonService = lessonService;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        if (!telegramUserService.isActiveUser(chatId)) {
            sendBotMessageService.sendMessage(chatId, "Введите команду /start");
            return;
        }
        if(telegramUserService.findByChatId(chatId).get().getIdGroup()==-1)
        {
            sendBotMessageService.sendMessage(chatId, "Выберите группу!!! /group");
            return;
        }
        TelegramUser user = telegramUserService.findByChatId(update.getMessage().getChatId().toString()).get();
        List<Lesson> listLessons = lessonService.findLessons(0, user.getIdGroup());
        StringBuffer lessons = new StringBuffer();
        for (Lesson lesson : listLessons) {
            lessons.append(lesson.getLessonNumber() + ")" + lesson.getLessonName() + " " + lesson.getCabinetNumber());
            lessons.append("\n");
        }
        Message message = new Message();
        message.setText(lessons.toString());
        sendBotMessageService.sendMessage(chatId, lessons.toString());
    }
}
