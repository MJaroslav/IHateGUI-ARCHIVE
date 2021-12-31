package com.github.mjaroslav.ihategui.model;

import com.github.mjaroslav.ihategui.util.ParsingHelper;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Data
public final class Dimension {
    @NotNull
    private Type type = Type.MATCH_PARENT;
    @NotNull
    private String value = type.toString();

    public int asConstant() {
        return Integer.parseInt(value);
    }

    public float asPercent() {
        return Float.parseFloat(value.substring(0, value.length() - 1));
    }

    public boolean isMatchingParent() {
        return type == Type.MATCH_PARENT;
    }

    public boolean isMatchingContent() {
        return type == Type.MATCH_CONTENT;
    }

    public boolean isConstant() {
        return type == Type.CONSTANT;
    }

    public boolean isPercent() {
        return type == Type.PERCENT;
    }

    public void set(@NotNull String value) {
        type = Type.of(value);
        this.value = value;
    }

    public static Dimension of(@NotNull String value) {
        val result = new Dimension();
        result.set(value);
        return result;
    }

    public enum Type {
        MATCH_PARENT, MATCH_CONTENT, CONSTANT, PERCENT;

        @NotNull
        public static Type of(@Nullable String value) {
            return ParsingHelper.parseTextAndNumericEnum(value, MATCH_PARENT, CONSTANT, PERCENT, Type::valueOf);
        }
    }
}
