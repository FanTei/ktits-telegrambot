package com.github.ktitsbot.kstb.service;

import com.github.ktitsbot.kstb.repository.LessonRepository;
import com.github.ktitsbot.kstb.repository.entity.Lesson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

@DisplayName("Unit-level testing for LessonService")
public class LessonServiceTest {
    private LessonRepository lessonRepository;

    @BeforeEach
    public void init() {
        lessonRepository = Mockito.mock(LessonRepository.class);
    }

    @Sql({"/sql/lesson.sql"})
    @Test
    public void shouldProperlyFindLesson() {
        int idDayOfWeek = 0;
        int idGroup = 0;
        List<Lesson> find = lessonRepository.findByIdDayOfWikAndIdGroup(idDayOfWeek, idGroup);
        Assertions.assertEquals(0, find.size());
    }
}
