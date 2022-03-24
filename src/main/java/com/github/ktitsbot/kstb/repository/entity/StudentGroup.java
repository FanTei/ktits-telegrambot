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
    private static int id;
    @Id
    private int groupId;

    @Column(name = "group_number")
    private int groupNumber;

    public StudentGroup(int groupNumber) {
        this.groupNumber = groupNumber;
        groupId = id++;
    }
}
