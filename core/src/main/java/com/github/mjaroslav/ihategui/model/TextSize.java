package com.github.mjaroslav.ihategui.model;

import com.github.mjaroslav.ihategui.util.ParsingHelper;
import lombok.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@EqualsAndHashCode
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TextSize {
    public static final int DEFAULT_SIZE = 10;

    @NotNull
    private Type type = Type.DEFAULT;
    @NotNull
    private String value = type.toString();

    public boolean isDefault() {
        return type == Type.DEFAULT;
    }

    public boolean isConstant() {
        return type == Type.CONSTANT;
    }

    public boolean isPercent() {
        return type == Type.PERCENT;
    }

    public int asConstant() {
        return Integer.parseInt(value);
    }

    public float asPercent() {
        return Float.parseFloat(value.substring(0, value.length() - 1));
    }

    public void set(@NotNull String value) {
        type = Type.of(value);
        this.value = value;
    }

    @NotNull
    public static TextSize of(@NotNull String value) {
        val result = new TextSize();
        result.set(value);
        return result;
    }

    public enum Type {
        DEFAULT, CONSTANT, PERCENT;

        @NotNull
        public static Type of(@Nullable String value) {
            return ParsingHelper.parseTextAndNumericEnum(value, DEFAULT, CONSTANT, PERCENT, Type::valueOf);
        }
    }
}
