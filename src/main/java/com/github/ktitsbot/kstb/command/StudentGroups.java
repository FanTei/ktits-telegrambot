package com.github.ktitsbot.kstb.command;

public enum StudentGroups {
    group202(0),
    group203(1),
    group211(2),
    grop205(3),
    group215(4),
    grop220(5);
    private final int id;

    StudentGroups(int id) {
        this.id = id;
    }

    public String getId() {
        return Integer.toString(id);
    }
}
