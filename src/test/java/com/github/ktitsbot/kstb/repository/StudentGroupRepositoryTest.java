package com.github.ktitsbot.kstb.repository;

import com.github.ktitsbot.kstb.repository.entity.StudentGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StudentGroupRepositoryTest {
    @Autowired
    StudentGroupRepository studentGroupRepository;

    @Sql(scripts = {"/sql/clearDbs.sql"})
    @Test
    public void shouldProperlySaveStudentGroup() {
        StudentGroup studentGroup = new StudentGroup();
        studentGroup.setGroupId(0);
        studentGroup.setGroupNumber(0);
        studentGroup.setCourse(0);
        studentGroupRepository.save(studentGroup);

        Optional<StudentGroup> saved = studentGroupRepository.findById(studentGroup.getGroupId());

        Assertions.assertTrue(saved.isPresent());
        Assertions.assertEquals(studentGroup, saved.get());
    }
}
