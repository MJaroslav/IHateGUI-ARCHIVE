package com.github.mjaroslav.ihategui.model;

import blue.endless.jankson.JsonObject;
import com.github.mjaroslav.ihategui.util.JsonHelper;
import lombok.Data;

@Data
public class Dimension {
    protected Type type = Type.MATCH_PARENT;
    protected int value = 0;

    public void loadFromJson(JsonObject object) {
        if (object == null)
            return;
        type = Type.fromName(JsonHelper.getOrDefault(object, "type", type.toString()));
        value = JsonHelper.getOrDefault(object, "value", value);
    }

    public enum Type {
        MATCH_PARENT, MATCH_CONTENT, VALUE;

        public static Type fromName(String name) {
            return valueOf(name.toUpperCase());
        }
    }
}
