package com.github.ktitsbot.kstb.service;

import com.github.ktitsbot.kstb.repository.StudentGroupRepository;
import com.github.ktitsbot.kstb.repository.entity.StudentGroup;

public class StudentGroupServiceImpl implements StudentGroupService{
    private final StudentGroupRepository studentGroupRepository;

    public StudentGroupServiceImpl(StudentGroupRepository studentGroupRepository) {
        this.studentGroupRepository = studentGroupRepository;
    }
    @Override
    public void save(StudentGroup studentGroup) {
        studentGroupRepository.save(studentGroup);
    }
}
