package com.github.ktitsbot.kstb.repository.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@NoArgsConstructor
@Table(name = "student_group")
public class StudentGroup {

    @Id
    private int groupId;

    @Column(name = "group_number")
    private int groupNumber;

    public StudentGroup(int id,int groupNumber) {
        this.groupNumber = groupNumber;
        this.groupId = id;
    }
}
