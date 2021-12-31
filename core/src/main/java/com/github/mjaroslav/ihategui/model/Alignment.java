package com.github.mjaroslav.ihategui.model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public enum Alignment {
    TOP, BOTTOM, LEFT, RIGHT, CENTER, TOP_LEFT, TOP_RIGHT,
    BOTTOM_LEFT, BOTTOM_RIGHT;

    @NotNull
    public static Alignment of(@Nullable String value) {
        if (value == null)
            return TOP_LEFT;
        try {
            return valueOf(value.toUpperCase());
        } catch (IllegalArgumentException ignored) {
            return TOP_LEFT;
        }
    }
}
