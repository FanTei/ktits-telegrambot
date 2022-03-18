package com.github.ktitsbot.kstb;

import com.github.ktitsbot.kstb.repository.entity.StudentGroup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelParser {
    private final List<StudentGroup> studentGroups;

    public ExcelParser() {
       studentGroups = new ArrayList<>();
    }

    public List<StudentGroup> getStudentGroups() {
        return studentGroups;
    }

    public  void parse() {
        try (FileInputStream inputStream = new FileInputStream("C:\\Users\\123\\Downloads\\test.xlsx")) {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);
            getGroup(sheet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private  void getLesson(int column, XSSFSheet sheet) {
        String dayOfWeek = "-1";
        for (int c = column - 1; c < column + 1; c++) {
            int count = 1;
            for (int r = 7; r < 42 || r < sheet.getPhysicalNumberOfRows() - 1; r++) {
                Row row = sheet.getRow(r);
                if (!dayOfWeek.equals(getDayOfWeek(r))) {
                    dayOfWeek = getDayOfWeek(r);
                    System.out.println(dayOfWeek);
                }
                String lesson = row.getCell(column).getStringCellValue();
                if (lesson.split("\n").length > 1)
                    lesson = lesson.split("\n")[1];
                if (lesson.equals(""))
                    System.out.println(count + ")null");
                else {
                    String cabinet = row.getCell(column - 1).toString();
                    if (cabinet.split("\n").length > 1)
                        cabinet = cabinet.split("\n")[1];
                    System.out.println(count + ")" + lesson + " №" + cabinet);
                }
                if (count == 6)
                    count = 0;
                count++;
            }
        }
    }

    private  void getGroup(XSSFSheet sheet) {
        int m = 1;
        Row row = sheet.getRow(6);
        for (int c = 0; c < row.getLastCellNum(); c++) {
            if (c % 3 == 0 && c < 19 && c > 0) {
                int groupNumber = Integer.parseInt(row.getCell(c).getStringCellValue().split("\n")[0].split(" ")[0]);
                System.out.println("Группа:" + groupNumber);
                studentGroups.add(new StudentGroup(m++,groupNumber));
                getLesson(c, sheet);
            }
        }
    }

    private  String getDayOfWeek(int row) {
        boolean monday = row > 6 && row < 13;
        boolean tuesday = row > 12 && row < 19;
        boolean wednesday = row > 18 && row < 25;
        boolean thursday = row > 24 && row < 31;
        boolean friday = row > 32 && row < 37;
        boolean saturday = row > 38 && row < 43;
        if (monday)
            return "Понедельник";
        else if (tuesday)
            return "Вторинк";
        else if (wednesday)
            return "Среда";
        else if (thursday)
            return "Четверг";
        else if (friday)
            return "Пятница";
        else if (saturday)
            return "Суббота";
        else return "error";
    }
}
