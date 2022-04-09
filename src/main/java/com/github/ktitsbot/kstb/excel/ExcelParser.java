package com.github.ktitsbot.kstb.excel;

import com.github.ktitsbot.kstb.repository.entity.Lesson;
import com.github.ktitsbot.kstb.repository.entity.StudentGroup;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;

import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class ExcelParser {
    private final List<StudentGroup> studentGroups;
    private final List<Lesson> lessons;
    private int startSheetRow = 0;
    private int stopSheetRow = 0;
    private final String path;
    private boolean isPeresmenka = false;
    private final List<String> lessonNamesHaveGroups = new ArrayList<>();

    public List<StudentGroup> getStudentGroups() {
        return studentGroups;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void clearLists() {
        studentGroups.clear();
        lessons.clear();
    }

    public ExcelParser(String path) {
        this.path = path;
        studentGroups = new ArrayList<>();
        lessons = new ArrayList<>();
        addLessonNamesHaveGroups();
        parse();
    }

    private void addLessonNamesHaveGroups() {
        lessonNamesHaveGroups.add("Родной язык");
        lessonNamesHaveGroups.add("Иностранный язык");
    }

    private boolean isLessonHaveGroups(String lessonName) {
        return lessonNamesHaveGroups.stream().anyMatch(l -> l.equals(lessonName));
    }

    public void parse() {
        System.out.println(path);
        try (Stream<Path> paths = Files.walk(Paths.get(path))) {
            System.out.println(paths);
            List<Path> list = paths.filter(Files::isRegularFile).toList();
            for (Path path : list) {
                XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(path.toString()));
                for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                    getGroups(workbook.getSheetAt(i));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getLessonsByGroup(int column, XSSFSheet sheet) {
        for (int c = column - 1; c < column; c++) {
            DayOfWeek dayOfWeek = DayOfWeek.Monday;
            for (int r = getStartRowIndex(sheet) + 1; r < getStopRowIndex(sheet); r++) {
                Row row = sheet.getRow(r);
                String str = row.getCell(0).toString().trim();
                if (!str.equals("")) {
                    dayOfWeek = DayOfWeek.getDayOfWeekByString(str);
                }
                Lesson lesson = getLesson(row, column, dayOfWeek);
                if (lesson != null) {
                    lessons.add(lesson);
                }
            }
        }
    }

    private void getGroups(XSSFSheet sheet) throws InterruptedException {
        int startSheetColumn = getStartColumnIndex(sheet);
        int stopSheetColumn = getStopColumnIndex(sheet);
        startSheetRow = getStartRowIndex(sheet);
        stopSheetRow = getStopRowIndex(sheet);
        Row row = sheet.getRow(startSheetRow);
        Thread.sleep(100);
        for (int column = startSheetColumn; column < stopSheetColumn; column += 3) {
            String cellValue = row.getCell(column).getStringCellValue().split("\n")[0].split(" ")[0];
            String[] split = cellValue.split("-");
            int groupNumber;
            if (split.length > 1)
                groupNumber = (Integer.parseInt(split[0]));
            else
                groupNumber = Integer.parseInt(cellValue);
            int course = Integer.parseInt(Integer.toString(groupNumber).substring(0, 1));
            studentGroups.add(new StudentGroup(groupNumber, course));
            getLessonsByGroup(column, sheet);
        }
    }

    private String getLessonName(Cell cell) {
        String lessonName = cell.toString();
        if (lessonName.equals(""))
            return null;
        String[] split = lessonName.split("\n");
        if (split.length > 1) {
            if (!isPeresmenka)
                lessonName = split[0];
            else lessonName = split[1];
        }
        if (lessonName.chars().anyMatch(c -> c == (int) '_')) {
            if (lessonName.chars().mapToObj(c -> (char) c).anyMatch(Character::isLetter)) {
                return lessonName.substring(0, lessonName.indexOf('_'));
            } else return null;
        }
        return lessonName;
    }

    private String getCabinet(Cell cell, String lessonName) {
        String cabinetStr = cell.toString();
        if (cabinetStr == null)
            return null;
        if (cabinetStr.length() < 3)
            return null;
        String[] split = cabinetStr.split("\n");
        if (split.length == 1) {
            cabinetStr = split[0];
        }
        if (split.length == 2) {
            if (isLessonHaveGroups(lessonName))
                cabinetStr = cabinetStr;
            else if (!isPeresmenka)
                cabinetStr = split[0];
            else cabinetStr = split[1];
        }
        if (cabinetStr.substring(cabinetStr.length() - 2, cabinetStr.length() - 1).equals("."))
            return cabinetStr.substring(0, cabinetStr.length() - 2);
        else
            return cabinetStr;
    }

    private Lesson getLesson(Row row, int column, DayOfWeek dayOfWeek) {
        String lessonName = getLessonName(row.getCell(column));
        if (lessonName == null)
            return null;
        String cabinet = getCabinet(row.getCell(column - 1), lessonName);
        int number = getLessonNumber(row.getCell(1));
        return new Lesson(studentGroups.get(studentGroups.size() - 1).getGroupId(),
                dayOfWeek.getId(), lessonName, cabinet, number);
    }

    private String getDayOfWeek(Row row) {
        String value = row.getCell(0).toString().trim();
        if (!value.equals(""))
            return value;
        else return null;
    }


    private int getLessonNumber(Cell cell) {
        String cellValue = cell.toString();
        if (!cellValue.equals("")) {
            return Integer.parseInt(cellValue.substring(0, 1));
        }
        return -100;
    }

    private int getStartRowIndex(Sheet sheet) {
        for (int r = 0; r < sheet.getPhysicalNumberOfRows(); r++) {
            Cell cell = sheet.getRow(r).getCell(0);
            if (cell != null) {
                String firstSellInRowValue = cell.getStringCellValue();
                if (firstSellInRowValue.equals("Дни недели"))
                    return r;
            }
        }
        return -1;
    }

    private int getStopRowIndex(Sheet sheet) {
        for (int r = 41; r < sheet.getPhysicalNumberOfRows(); r++) {
            List<String> cellsValues = new ArrayList<>();
            Row row = sheet.getRow(r);
            for (Cell cell : row) {
                cellsValues.add(cell.toString());
            }
            if (cellsValues.stream().allMatch(i -> i.equals("")))
                return r;
        }
        return -1;
    }

    private int getStartColumnIndex(Sheet sheet) {
        Row row = sheet.getRow(getStartRowIndex(sheet));
        for (int c = 0; c < row.getLastCellNum(); c++) {
            if (row.getCell(c).toString().equals("Ауд."))
                return c + 1;
        }
        return -1;
    }

    private int getStopColumnIndex(Sheet sheet) {
        int startRowIndex = getStartRowIndex(sheet);
        Row row = sheet.getRow(startRowIndex);
        for (; startRowIndex < row.getLastCellNum(); startRowIndex++) {
            if (row.getCell(startRowIndex).toString().equals("Пара"))
                return startRowIndex;
        }
        return -1;
    }
}

