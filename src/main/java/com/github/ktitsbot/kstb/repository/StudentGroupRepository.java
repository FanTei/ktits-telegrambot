package com.github.ktitsbot.kstb.repository;

import com.github.ktitsbot.kstb.repository.entity.StudentGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentGroupRepository extends JpaRepository<StudentGroup,Integer> {

}
