package com.github.ktitsbot.kstb.excel;

import com.github.ktitsbot.kstb.repository.LessonRepository;
import com.github.ktitsbot.kstb.repository.StudentGroupRepository;
import com.github.ktitsbot.kstb.repository.entity.Lesson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class ExcelApplication implements CommandLineRunner {
    @Autowired
    StudentGroupRepository studentGroupRepository;
    @Autowired
    LessonRepository lessonRepository;

    public StudentGroupRepository getStudentGroupRepository() {
        return studentGroupRepository;
    }

    public LessonRepository getLessonRepository() {
        return lessonRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        ExcelParser excelParser = new ExcelParser();
        List<Lesson> lessons = excelParser.getLessons().stream().filter(Objects::nonNull).toList();
        studentGroupRepository.saveAll(excelParser.getStudentGroups());
        lessonRepository.saveAll(lessons);
        System.out.println("Все");
        excelParser.clearLists();
    }
}
