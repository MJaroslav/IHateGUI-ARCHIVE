package com.github.mjaroslav.ihategui.util;

import blue.endless.jankson.JsonObject;

public class JsonHelper {
    @SuppressWarnings("unchecked")
    public static <T> T getOrDefault(JsonObject object, String key, T defaultValue) {
        return object.containsKey(key) ? (T) object.get(defaultValue.getClass(), key) : defaultValue;
    }
}
