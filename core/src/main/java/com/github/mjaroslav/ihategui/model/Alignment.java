package com.github.mjaroslav.ihategui.model;

public enum Alignment {
    CENTER_TOP, CENTER_BOTTOM, MIDDLE_LEFT, MIDDLE_RIGHT, CENTER, TOP_LEFT, TOP_RIGHT,
    BOTTOM_LEFT, BOTTOM_RIGHT;

    public static Alignment fromValue(String value) {
        try {
            return valueOf(value);
        } catch (IllegalArgumentException ignored) {
            return TOP_LEFT;
        }
    }
}
