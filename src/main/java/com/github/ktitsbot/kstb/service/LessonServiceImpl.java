package com.github.ktitsbot.kstb.service;

import com.github.ktitsbot.kstb.excel.DayOfWeek;
import com.github.ktitsbot.kstb.repository.LessonRepository;
import com.github.ktitsbot.kstb.repository.entity.Lesson;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.List;

@Service
public class LessonServiceImpl implements LessonService {
    private final LessonRepository lessonRepository;

    @Autowired
    public LessonServiceImpl(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    @Override
    public List<Lesson> findLessons(int idDayOfWeek, int idGroup) {
        return lessonRepository.findByIdDayOfWikAndIdGroup(idDayOfWeek, idGroup);
    }

    @Override
    public String lessonsToString(List<Lesson> lessons) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Lesson lesson : lessons) {
            int lessonNumber = lesson.getLessonNumber();
            String lessonName = lesson.getLessonName();
            int lessonCabinetNumber = lesson.getCabinetNumber();
            stringBuilder.append(lesson.getLessonNumber()).append(")").append(lessonName).append(" ").append(lessonCabinetNumber);
            stringBuilder.append("\n");
        }
        return "<b>" + stringBuilder.toString() + "</b>";
    }
}
