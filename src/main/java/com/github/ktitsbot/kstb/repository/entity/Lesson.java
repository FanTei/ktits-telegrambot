package com.github.ktitsbot.kstb.repository.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "lesson")
@NoArgsConstructor
public class Lesson {
    private static int id;
    @Id
    private int idLesson;
    @Column(name = "id_group")
    private int idGroup;
    @Column(name = "id_day_of_week")
    private int idDayOfWik;
    @Column(name = "lesson_name")
    private String lessonName;
    @Column(name = "lesson_cabinet")
    private int cabinetNumber;
    @Column(name = "lesson_number")
    private int lessonNumber;

    public Lesson(int idGroup, int idDayOfWik, String lessonName, int cabinetNumber, int lessonNumber) {
        this.idGroup = idGroup;
        this.idDayOfWik = idDayOfWik;
        this.lessonName = lessonName;
        this.cabinetNumber = cabinetNumber;
        this.lessonNumber = lessonNumber;
        idLesson = id++;
    }
}