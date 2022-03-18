package com.github.ktitsbot.kstb;

import com.github.ktitsbot.kstb.repository.StudentGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Save implements CommandLineRunner {
@Autowired
StudentGroupRepository studentGroupRepository;
    @Override
    public void run(String... args) throws Exception {
        ExcelParser excelParser = new ExcelParser();
        excelParser.parse();
       studentGroupRepository.saveAll(excelParser.getStudentGroups());
    }
}
