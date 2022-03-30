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

    public static DayOfWeek getByRow(int row, int startRow) {
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

    public static DayOfWeek getById(int id) {
        return DayOfWeek.values()[id];
    }
}
