package com.github.mjaroslav.ihategui.model;

import blue.endless.jankson.JsonObject;
import com.github.mjaroslav.ihategui.util.JsonHelper;
import lombok.Data;

@Data
public class Sided {
    protected int top = 0;
    protected int bottom = 0;
    protected int left = 0;
    protected int right = 0;

    public void loadFromJson(JsonObject object) {
        if (object == null)
            return;
        top = JsonHelper.getOrDefault(object, "top", top);
        bottom = JsonHelper.getOrDefault(object, "bottom", bottom);
        left = JsonHelper.getOrDefault(object, "left", left);
        right = JsonHelper.getOrDefault(object, "right", right);
    }
}
