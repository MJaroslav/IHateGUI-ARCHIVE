package com.github.mjaroslav.ihategui.model;

import blue.endless.jankson.JsonObject;
import com.github.mjaroslav.ihategui.util.JsonHelper;
import lombok.Data;

@Data
public abstract class Element {
    protected final Dimension width = new Dimension();
    protected final Dimension height = new Dimension();
    protected final Sided padding = new Sided();
    protected final Sided margin = new Sided();
    protected String id = "";
    protected boolean enabled;
    protected boolean visible;

    public void loadFromJson(JsonObject object) {
        width.loadFromJson(object.getObject("width"));
        height.loadFromJson(object.getObject("height"));
        padding.loadFromJson(object.getObject("padding"));
        margin.loadFromJson(object.getObject("margin"));
        id = JsonHelper.getOrDefault(object, "id", id);
        enabled = JsonHelper.getOrDefault(object, "enabled", enabled);
        visible = JsonHelper.getOrDefault(object, "visible", visible);
    }
}
