package ru.mboychook.webQuestions.models;

import java.util.EnumSet;
import java.util.Optional;

public enum RoleEnum {


    ADMIN("ADMIN", 1),
    USER("USER", 2),
    GUEST("GUEST", 3);

    private final String name;

    private final int index;

    RoleEnum(String value, int index) {
        name = value;
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }

    public static RoleEnum getByUpperCaseName(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }

        return RoleEnum.valueOf(name.toUpperCase());
    }

    public static RoleEnum getByIndex(int index) {
        if (index == 0) {
            return null;
        }
        return RoleEnum.values()[index];
    }

    static public boolean isMember(String value) {
        return EnumSet.allOf(RoleEnum.class).stream()
                .anyMatch(roleEnum -> roleEnum.toString().equals(value.toUpperCase()));
    }

    @Override
    public String toString() {
        return name;
    }

    /*ADMIN("ADMIN", 1),
    USER("USER", 2),
    GUEST("GUEST", 3);

    private final String name;

    private final int index;

    RoleEnum(String value, int index) {
        this.name = value;
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }

    public static RoleEnum getByUpperCaseName(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }

        return RoleEnum.valueOf(name.toUpperCase());

        *//*if (isMember(name)) {
            return Optional.of(RoleEnum.valueOf(name.toUpperCase()));
        }
        return Optional.empty();*//*
    }

    public static RoleEnum convertIntToStringEnum(int index) {
        if (index == 0) {
            return null;
        }
        return RoleEnum.values()[index];
    }

    static public boolean isMember(String value) {
        return EnumSet.allOf(DifficultyEnum.class)
                      .stream()
                      .anyMatch(difficultyEnum -> difficultyEnum.toString().equals(value.toUpperCase()));
    }

    @Override
    public String toString() {
        return name;
    }*/
}
