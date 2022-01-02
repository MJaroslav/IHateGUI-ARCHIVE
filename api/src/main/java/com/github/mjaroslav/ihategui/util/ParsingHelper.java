package com.github.mjaroslav.ihategui.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class ParsingHelper {
    @NotNull
    public static <T> T parseTextAndNumericEnum(@Nullable String value, @NotNull T defaultValue,
                                                @NotNull T constantValue, @NotNull T percentValue,
                                                @NotNull Function<String, T> nameValueFunc) {
        if (value == null)
            return defaultValue;
        try {
            return nameValueFunc.apply(value.toUpperCase());
        } catch (IllegalArgumentException ignored) {
            try {
                if (value.endsWith("%")) {
                    Float.parseFloat(StringUtils.sub(value, -1));
                    return percentValue;
                } else {
                    Integer.parseInt(value);
                    return constantValue;
                }
            } catch (NumberFormatException ignored1) {
                return defaultValue;
            }
        }
    }
}
