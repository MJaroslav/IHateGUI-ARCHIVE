package com.github.mjaroslav.ihategui.jankson;

import blue.endless.jankson.Jankson;
import blue.endless.jankson.JsonObject;
import blue.endless.jankson.JsonPrimitive;
import lombok.val;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class JsonElementUtils {
    public static final String KEY_TEST = "test";

    @Nullable
    public static <T> T buildObjectForTests(@NotNull Jankson jankson, @NotNull Class<T> resultType, @NotNull Object value) {
        val result = new JsonObject();
        result.setMarshaller(jankson.getMarshaller());
        result.put(KEY_TEST, new JsonPrimitive(value));
        return result.get(resultType, KEY_TEST);
    }
}
