package com.github.ktitsbot.kstb.excel;

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

    public static DayOfWeek getDayOfWeekByRow(int row,int startRow) {
        DayOfWeek result;
        if (row > startRow && row <= (startRow+=6))
            //start row> <start +7
            result = Monday;
        else if (row > startRow && row <= (startRow+=6))
            result = Tuesday;
        else if (row > startRow && row < (startRow+=6))
            result = Wednesday;
        else if (row > startRow && row < (startRow+=6))
            result = Thursday;
        else if (row > startRow && row < (startRow+=6))
            result = Friday;
        else
            result = Saturday;
        return result;
    }
}
