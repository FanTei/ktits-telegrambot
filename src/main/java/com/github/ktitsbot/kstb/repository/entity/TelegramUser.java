package com.github.ktitsbot.kstb.repository.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "tg_user")
public class TelegramUser {//Telegram User entity
    @Id
    @Column(name = "chat_id")
    private String charId;

    @Column(name = "active")
    private boolean active;
}
