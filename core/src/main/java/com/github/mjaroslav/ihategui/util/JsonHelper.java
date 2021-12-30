package com.github.mjaroslav.ihategui.util;

import blue.endless.jankson.JsonObject;
import lombok.NonNull;

public class JsonHelper {
    @SuppressWarnings("unchecked")
    public static <T> T getOrDefault(JsonObject object, String key, @NonNull T defaultValue) {
        return object.containsKey(key) ? (T) object.get(defaultValue.getClass(), key) : defaultValue;
    }
}
