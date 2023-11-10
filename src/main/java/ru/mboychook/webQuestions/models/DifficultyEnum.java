package ru.mboychook.webQuestions.models;

import java.util.EnumSet;

public enum DifficultyEnum {
    //EASY, MEDIUM, HARD;

    EASY("EASY", 1),
    MEDIUM("MEDIUM", 2),
    HARD("HARD", 3);

    private final String name;

    private final int index;

    DifficultyEnum(String value, int index) {
        name = value;
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }

    public static DifficultyEnum getByUpperCaseName(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }

        return DifficultyEnum.valueOf(name.toUpperCase());
    }

    public static DifficultyEnum getByIndex(int index) {
        if (index == 0) {
            return null;
        }
        return DifficultyEnum.values()[index];
    }

    static public boolean isMember(String value) {
        return EnumSet.allOf(DifficultyEnum.class).stream()
                .anyMatch(difficultyEnum -> difficultyEnum.toString().equals(value.toUpperCase()));
    }

    @Override
    public String toString() {
        return name;
    }
}
