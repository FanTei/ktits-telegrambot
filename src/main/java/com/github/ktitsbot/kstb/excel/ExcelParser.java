package com.github.ktitsbot.kstb.excel;

import com.github.ktitsbot.kstb.repository.entity.Lesson;
import com.github.ktitsbot.kstb.repository.entity.StudentGroup;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class ExcelParser {
    private final List<StudentGroup> studentGroups;
    private final List<Lesson> lessons;
    private int startSheetRow = 0;

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

    public ExcelParser() {
        studentGroups = new ArrayList<>();
        lessons = new ArrayList<>();
        parse();
    }

    public void parse() {
        try (Stream<Path> paths = Files.walk(Paths.get("C:\\Users\\123\\Desktop\\lessons"))) {
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
            int lessonNumber = 1;
            for (int r = getStartRowIndex(sheet) + 1; r < getStopRowIndex(sheet) - 2; r++) {
                Row row = sheet.getRow(r);
                Lesson lesson = getLesson(row, column, lessonNumber);
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
        String lessonName = cell.getStringCellValue();
        if (lessonName.equals(""))
            return null;
        String[] split = lessonName.split("\n");
        if (split.length > 1) {
            lessonName = split[0];
        }
        if (lessonName.chars().anyMatch(c -> c == (int) '_')) {
            return null;
        }
        return lessonName;
    }

    private int getCabinet(Cell cell) {
        int cabinet = -1;
        String cabinetStr = cell.toString();
        String[] split = cabinetStr.split("\n");
        try {
            if (split.length > 1) {
                if (split[0].equals("с/з") || split[0].equals("точка кипения"))
                    cabinet = 0;
                else
                    cabinet = (int) Math.floor(Float.parseFloat(split[1]));
            } else
                cabinet = (int) Math.floor(Float.parseFloat(cabinetStr));
        } catch (Exception e) {
            return -1;
        }
        return cabinet;
//        if (cabinetStr.equals(""))
//            return -1;
//        if (cabinetStr.equals("с/з") || cabinetStr.equals("точка кипения"))
//            return 0;
    }

    private Lesson getLesson(Row row, int column, int lessonNumber) {
        String lessonName = getLessonName(row.getCell(column));
        int cabinet = getCabinet(row.getCell(column - 1));
        if (lessonName == null || cabinet == -1)
            return null;
        int number = getLessonNumber(row.getCell(1));
        DayOfWeek dayOfWeek = DayOfWeek.getByRow(row.getRowNum(), startSheetRow);
        return new Lesson(studentGroups.get(studentGroups.size() - 1).getGroupId(),
                dayOfWeek.getId(), lessonName, cabinet, number);
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

