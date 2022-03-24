package com.github.ktitsbot.kstb.service;

import com.github.ktitsbot.kstb.repository.entity.Lesson;

import java.util.List;

public interface LessonService {
        List<Lesson> findLessons(int idDayOfWik, int idGroup);
}
