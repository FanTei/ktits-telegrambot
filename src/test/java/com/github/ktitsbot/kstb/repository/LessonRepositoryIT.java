package com.github.ktitsbot.kstb.repository;

import com.github.ktitsbot.kstb.repository.entity.Lesson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LessonRepositoryIT {
    @Autowired
    private LessonRepository lessonRepository;

    @Sql(scripts = {"/sql/lesson.sql"})
    @Test
    public void shouldProperlyFindByIdDayOfWikAndIdGroup() {
        List<Lesson> lessons = lessonRepository.findByIdDayOfWikAndIdGroup(0, 0);
        Assertions.assertEquals(3, lessons.size());
    }

    @Sql(scripts = {"/sql/clearDbs.sql"})
    @Test
    public void shouldProperlySaveLesson() {
        Lesson lesson = new Lesson();
        lesson.setIdLesson(0);
        lesson.setLessonName("TestName");
        lesson.setLessonNumber(0);
        lesson.setCabinetNumber("TestCabinet");
        lesson.setIdGroup(0);
        lesson.setIdDayOfWik(0);
        lessonRepository.save(lesson);
        Optional<Lesson> saved = lessonRepository.findById(lesson.getIdLesson());

        Assertions.assertTrue(saved.isPresent());
        Assertions.assertEquals(lesson, saved.get());
    }
}
