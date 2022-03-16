package com.github.ktitsbot.kstb.command;

import com.github.ktitsbot.kstb.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class HelpCommand implements Command{
    private final SendBotMessageService sendBotMessageService;

    public  static  final  String HELP_MESSAGE = String.format("<b> Доступные команды</b>\n\n"
            + "<b>Начать\\закончить работу с ботом</b>\n"
            + "%s - начать работу со мной\n"
            + "%s - приостановить работу со мной\n\n"
            + "%s - получить помощь в работе со мной\n"
            + "%s - получить статистику \n",
            CommandName.START.getCommandName(),CommandName.STOP.getCommandName(),CommandName.HELP.getCommandName(),CommandName.STAT.getCommandName());

    public HelpCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), HELP_MESSAGE);
    }
}

