package com.github.ktitsbot.kstb.service;

import com.github.ktitsbot.kstb.repository.StudentGroupRepository;
import com.github.ktitsbot.kstb.repository.entity.StudentGroup;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class StudentGroupServiceImpl implements StudentGroupService {

    private final StudentGroupRepository studentGroupRepository;

    public StudentGroupServiceImpl(StudentGroupRepository studentGroupRepository) {
        this.studentGroupRepository = studentGroupRepository;
    }

    @Override
    public List<StudentGroup> getAllStudentGroups() {
        return studentGroupRepository.findAll();
    }
}
