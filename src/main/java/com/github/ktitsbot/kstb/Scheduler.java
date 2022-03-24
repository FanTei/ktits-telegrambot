package com.github.ktitsbot.kstb;

import com.github.ktitsbot.kstb.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Scheduler {

    private final SendBotMessageService sendBotMessageService;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    public static void main(String[] args) {
        ScheduledExecutorService x = Executors.newScheduledThreadPool(1);
        System.out.println(x);
    }
    private void dfg(){
        System.out.println(scheduler);
    }
    public Scheduler(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }
    public void test(String chatId) {
        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                sendBotMessageService.sendMessage(chatId, "");
            }
        }, 1, 1, TimeUnit.MILLISECONDS);
    }

    private long timeToSendTimeTableInSeconds() {
        Calendar calendar = GregorianCalendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH-m-s");
        String time = simpleDateFormat.format(calendar.getTime());
        System.out.println(time);
        String[] split = time.split("-");
        long millisecon = Integer.parseInt(split[0]) * 3600000L + Integer.parseInt(split[1]) * 60000L + Integer.parseInt(split[2]) * 100L;
        // 24 - текущее время + 6;
        return (30 * 3600000) - millisecon;
    }
}
