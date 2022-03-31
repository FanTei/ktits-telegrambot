package com.github.ktitsbot.kstb.excel;

import java.util.Locale;

public enum DayOfWeek {
    Monday(0),
    Tuesday(1),
    Wednesday(2),
    Thursday(3),
    Friday(4),
    Saturday(5);
    private final int id;

    public int getId() {
        return id;
    }

    DayOfWeek(int id) {
        this.id = id;
    }

    public static DayOfWeek getByRow(int row, int startRow, int stopRow) {
        DayOfWeek result;
        if (row > startRow && row <= (startRow += 8))
            result = Monday;
        else if (row > startRow && row <= (startRow += 8))
            result = Tuesday;
        else if (row > startRow && row < (startRow += 8))
            result = Wednesday;
        else if (row > startRow && row < (startRow += 8))
            result = Thursday;
        else if (row > startRow && row < (startRow += 8))
            result = Friday;
        else
            result = Saturday;
        return result;
    }

    public static DayOfWeek getDayOfWeekByString(String stringDay) {
        final String mondayString = "понедельник";
        final String tuesdayString = "вторник";
        final String wednesdayString = "среда";
        final String thursdayString = "четверг";
        final String fridayString = "пятница";
        final String saturdayString = "суббота";
        return switch (stringDay.toLowerCase(Locale.ROOT)) {
            case mondayString -> Monday;
            case tuesdayString -> Tuesday;
            case wednesdayString -> Wednesday;
            case thursdayString -> Thursday;
            case fridayString -> Friday;
            case saturdayString -> Saturday;
            default -> Monday;
        };
    }

    public static DayOfWeek getById(int id) {
        return DayOfWeek.values()[id];
    }
}
