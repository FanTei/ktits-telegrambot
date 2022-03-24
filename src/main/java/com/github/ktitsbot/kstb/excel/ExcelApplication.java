package com.github.ktitsbot.kstb.excel;

import com.github.ktitsbot.kstb.repository.LessonRepository;
import com.github.ktitsbot.kstb.repository.StudentGroupRepository;
import com.github.ktitsbot.kstb.service.LessonService;
import com.github.ktitsbot.kstb.service.LessonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ExcelApplication implements CommandLineRunner {
    @Autowired
    StudentGroupRepository studentGroupRepository;
    @Autowired
    LessonRepository lessonRepository;
    private LessonService lessonService;

    public LessonRepository getLessonRepository() {
        return lessonRepository;
    }

    public ExcelApplication() {
        lessonService = new LessonServiceImpl(lessonRepository);
    }

    @Override
    public void run(String... args) throws Exception {
        ExcelParser excelParser = new ExcelParser();
        studentGroupRepository.saveAll(excelParser.getStudentGroups());
        lessonRepository.saveAll(excelParser.getLessons());
    }
}
