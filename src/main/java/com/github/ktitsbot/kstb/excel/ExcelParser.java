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
            int maxLessons = 0;
            for (int r = getStartRowIndex(sheet) + 1; r < getStopRowIndex(sheet); r++) {
                Row row = sheet.getRow(r);
                Lesson lesson = getLesson(row, column, lessonNumber);
                getMaxCountLessons(r, sheet);
                if (lesson != null) {
                    lessons.add(lesson);
                }
                if (lessonNumber == 6)
                    lessonNumber = 0;
                lessonNumber++;
            }
        }
    }

    private int getMaxCountLessons(int row, XSSFSheet sheet) {
        int[] numbers = new int[10];
        for (int n : numbers) {
            for (int r = row; r < row + 9 || r < getStopRowIndex(sheet)-2; r++) {
                Row row1 = sheet.getRow(r);
                n= Integer.parseInt(row1.getCell(1).toString().substring(0,1));

            }
        }

        return Arrays.stream(numbers).max().getAsInt();
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
        try {
            if (split.length > 1)
                return (int) Math.floor(Float.parseFloat(split[1]));
            else
                return (int) Math.floor(Float.parseFloat(cabinetStr));
        } catch (Exception e) {
            return -1;
        }
    }

    private Lesson getLesson(Row row, int column, int lessonNumber) {
        String lessonName = getLessonName(row.getCell(column));
        if (lessonName == null)
            return null;
        int cabinet = getCabinet(row.getCell(column - 1));
        if (cabinet == -1)
            return null;
        DayOfWeek dayOfWeek = DayOfWeek.getByRow(row.getRowNum(), startSheetRow);
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

