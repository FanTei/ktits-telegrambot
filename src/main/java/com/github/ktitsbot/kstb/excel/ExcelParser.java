package com.github.ktitsbot.kstb.excel;

import com.github.ktitsbot.kstb.repository.entity.Lesson;
import com.github.ktitsbot.kstb.repository.entity.StudentGroup;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

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

    public ExcelParser() {
        studentGroups = new ArrayList<>();
        lessons = new ArrayList<>();
        parse();
    }

    public void parse() {
        try (FileInputStream inputStream = new FileInputStream("C:\\Users\\123\\Downloads\\test.xlsx")) {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            for (int i = 1; i < workbook.getNumberOfSheets(); i++) {
                getGroups(workbook.getSheetAt(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getLessonsByGroup(int column, XSSFSheet sheet) {
        for (int c = column - 1; c < column; c++) {
            int lessonNumber = 1 ;
            for (int r = getStartRowIndex(sheet)+1; r < getStopRowIndex(sheet); r++) {
                Lesson lesson = getLesson(sheet.getRow(r), column, lessonNumber);
                if (lesson != null) {
                    lessons.add(lesson);
                }
                if (lessonNumber == 6)
                    lessonNumber = 0;
                lessonNumber++;
            }
        }
    }

    private void getGroups(XSSFSheet sheet) {
        int startSheetColumn = getStartColumnIndex(sheet);
        int stopSheetColumn = getStopColumnIndex(sheet);
        startSheetRow = getStartRowIndex(sheet);
        Row row = sheet.getRow(startSheetRow);
        for (int column = startSheetColumn; column < stopSheetColumn; column += 3) {
            int groupNumber = Integer.parseInt(row.getCell(column).getStringCellValue().split("\n")[0].split(" ")[0]);
            studentGroups.add(new StudentGroup(groupNumber));
            getLessonsByGroup(column, sheet);
        }
    }

    private String getLessonName(Cell cell) {
        String lessonName = cell.getStringCellValue();
        String[] split = lessonName.split("\n");
        if (split.length > 1)
            return split[1];
        if (lessonName.equals(""))
            return null;
        else
            return lessonName;
    }

    private int getCabinet(Cell cell) {
        String cabinetStr = cell.toString();
        if (cabinetStr.equals(""))
            return -1;
        if (cabinetStr.equals("с/з"))
            return 0;
        String[] split = cabinetStr.split("\n");
        if (split.length > 1)
            return (int) Math.floor(Float.parseFloat(split[1]));
        return (int) Math.floor(Float.parseFloat(cabinetStr));
    }
    private Lesson getLesson(Row row, int column, int lessonNumber) {
        String lessonName = getLessonName(row.getCell(column));
        if (lessonName == null)
            return null;
        int cabinet = getCabinet(row.getCell(column - 1));
        DayOfWeek dayOfWeek = DayOfWeek.getDayOfWeekByRow(row.getRowNum(), startSheetRow);
        return new Lesson(studentGroups.get(studentGroups.size() - 1).getGroupId(),
                dayOfWeek.getId(), lessonName, cabinet, lessonNumber);
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

