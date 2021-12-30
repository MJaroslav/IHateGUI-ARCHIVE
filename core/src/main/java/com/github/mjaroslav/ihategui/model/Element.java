package com.github.mjaroslav.ihategui.model;

import blue.endless.jankson.JsonObject;
import com.github.mjaroslav.ihategui.util.JsonHelper;
import com.github.mjaroslav.ihategui.view.ViewLoader;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(exclude = {"loader", "parent"})
@ToString(exclude = {"loader", "parent"})
@Data
public abstract class Element {
    //
    // View parameters
    //
    protected Dimension width = new Dimension();
    protected Dimension height = new Dimension();
    protected int x, y;
    protected Sided padding = new Sided();
    protected Sided margin = new Sided();
    protected String id = "";
    protected boolean enabled;
    protected boolean visible;
    protected Alignment alignment = Alignment.TOP_LEFT;
    protected JsonObject extra = new JsonObject();

    //
    // Internal parameters
    //
    protected Layout parent;
    protected ViewLoader loader;

    public void setWidth(String value) {
        width.loadFromValue(value);
    }

    public void setHeight(String value) {
        height.loadFromValue(value);
    }

    public void setPadding(String value) {
        padding.set(value);
    }

    public void setMargin(String value) {
        margin.set(value);
    }

    public void packToParent() {
        if (parent != null) {
            if (getWidth().getType() == Dimension.Type.MATCH_PARENT) {
                getWidth().setValue(parent.getWidth().getValue());
                setX(0);
            }
            if (getHeight().getType() == Dimension.Type.MATCH_PARENT) {
                getHeight().setValue(parent.getHeight().getValue());
                setY(0);
            }
        }
    }

    public void pack() {
        packToParent();
    }

    public void loadFromJson(JsonObject object) {
        loader = ViewLoader.getLoader(object.get(String.class, "loader"));
        width.loadFromValue(object.get(String.class, "width"));
        height.loadFromValue(object.get(String.class, "height"));
        padding.set(object.get(String.class, "padding"));
        margin.set(object.get(String.class, "margin"));
        id = JsonHelper.getOrDefault(object, "id", id);
        enabled = JsonHelper.getOrDefault(object, "enabled", enabled);
        visible = JsonHelper.getOrDefault(object, "visible", visible);
        x = JsonHelper.getOrDefault(object, "x", x);
        y = JsonHelper.getOrDefault(object, "y", y);
        alignment = Alignment.fromValue(JsonHelper.getOrDefault(object, "alignment", alignment.toString()));
        if (object.containsKey("extra"))
            extra.putAll(object.getObject("extra"));
    }
}
