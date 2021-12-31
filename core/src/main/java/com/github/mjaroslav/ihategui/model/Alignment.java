package com.github.mjaroslav.ihategui.model;

public enum Alignment {
    TOP, BOTTOM, LEFT, RIGHT, CENTER, TOP_LEFT, TOP_RIGHT,
    BOTTOM_LEFT, BOTTOM_RIGHT;

    public static Alignment fromValue(String value) {
        try {
            return valueOf(value.toUpperCase());
        } catch (IllegalArgumentException ignored) {
            return TOP_LEFT;
        }
    }
}
