package com.github.ktitsbot.kstb;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.FileInputStream;
@SpringBootApplication
public class KtitsTelegramBotApplication {
	public static void main(String[] args) {
		SpringApplication.run(KtitsTelegramBotApplication.class, args);
	}
}
