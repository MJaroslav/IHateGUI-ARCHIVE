package com.github.mjaroslav.ihategui.model;

public enum Orientation {
    VERTICAL, HORIZONTAL;

    public static Orientation fromName(String name) {
        return valueOf(name.toUpperCase());
    }
}
