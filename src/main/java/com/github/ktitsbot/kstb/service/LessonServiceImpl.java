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
}
