package com.github.ktitsbot.kstb.repository.entity;

import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.lang.NonNullApi;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@NoArgsConstructor

@Table(name = "student_group")
public class StudentGroup {

    private static int id = 1;
    @Id
    private int groupId;

    @Column(name = "group_number")
    private int groupNumber;
    @Column(name = "course")
    private int course;

    public StudentGroup(int groupNumber,int course) {
        this.groupNumber = groupNumber;
        this.course = course;
        groupId = id++;
    }
}
