package com.github.ktitsbot.kstb.repository;

import com.github.ktitsbot.kstb.repository.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

public interface LessonRepository extends JpaRepository<Lesson,Integer> {
    List<Lesson> findByIdDayOfWikAndIdGroup(int idDayOfWik, int idGroup);}
