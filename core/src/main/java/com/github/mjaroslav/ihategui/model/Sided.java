package com.github.mjaroslav.ihategui.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

@Data
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class Sided {
    private int top;
    private int bottom;
    private int left;
    private int right;

    public void set(int top, int bottom, int left, int right) {
        this.top = top;
        this.bottom = bottom;
        this.left = left;
        this.right = right;
    }

    public void set(int value) {
        set(value, value, value, value);
    }

    public void set(@NotNull String value) {
        val sides = Arrays.stream(value.split(";")).mapToInt(Integer::parseInt).toArray();
        if (sides.length == 1)
            top = bottom = left = right = sides[0];
        else if (sides.length == 2) {
            top = bottom = sides[0];
            left = right = sides[1];
        } else if (sides.length == 4) {
            top = sides[0];
            bottom = sides[1];
            left = sides[2];
            right = sides[3];
        }
    }

    @NotNull
    public static Sided of(@NotNull String value) {
        val result = new Sided();
        result.set(value);
        return result;
    }
}
