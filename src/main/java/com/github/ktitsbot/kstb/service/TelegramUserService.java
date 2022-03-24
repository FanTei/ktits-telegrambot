package com.github.ktitsbot.kstb.service;

import com.github.ktitsbot.kstb.repository.entity.TelegramUser;

import java.util.List;
import java.util.Optional;

public interface TelegramUserService {
    void save(TelegramUser telegramUser);

    List<TelegramUser> retrieveAllActiveUsers();

    Optional<TelegramUser> findByChatId(String chatId);

    boolean isActiveUser(String chaId);
}
