package com.github.mjaroslav.ihategui.model;

import blue.endless.jankson.JsonPrimitive;
import blue.endless.jankson.annotation.Deserializer;

public enum Orientation {
    HORIZONTAL, VERTICAL;

    @Deserializer
    public static Orientation deserialize(JsonPrimitive element) {
        return fromName(element.asString());
    }

    public static Orientation fromName(String name) {
        try {
            return valueOf(name.toUpperCase());
        } catch (IllegalArgumentException ignored) {
            return HORIZONTAL;
        }
    }
}
