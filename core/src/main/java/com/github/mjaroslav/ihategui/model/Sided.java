package com.github.mjaroslav.ihategui.model;

import blue.endless.jankson.JsonPrimitive;
import blue.endless.jankson.annotation.Deserializer;
import lombok.Data;
import lombok.val;

import java.util.Arrays;

@Data
public class Sided {
    protected int top = 0;
    protected int bottom = 0;
    protected int left = 0;
    protected int right = 0;

    public void set(int top, int bottom, int left, int right) {
        this.top = top;
        this.bottom = bottom;
        this.left = left;
        this.right = right;
    }

    public void set(int value) {
        set(value, value, value, value);
    }

    public void set(String value) {
        if(value == null)
            return;
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

    @Deserializer
    public static Sided deserialize(JsonPrimitive element) {
        String value = element.asString();
        val result = new Sided();
        result.set(value);
        return result;
    }
}
