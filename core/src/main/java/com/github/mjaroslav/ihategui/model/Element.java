package com.github.mjaroslav.ihategui.model;

import blue.endless.jankson.JsonObject;
import com.github.mjaroslav.ihategui.util.JsonHelper;
import com.github.mjaroslav.ihategui.view.ViewLoader;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.val;

import java.util.Objects;

@EqualsAndHashCode(exclude = {"loader", "parent", "clientWidth", "clientHeight"})
@ToString(exclude = {"loader", "parent", "clientWidth", "clientHeight"})
@Data
public abstract class Element {
    //
    // View parameters
    //
    protected final Dimension width = new Dimension();
    protected final Dimension height = new Dimension();
    protected final Sided padding = new Sided();
    protected final Sided margin = new Sided();
    protected String id = "";
    protected boolean enabled;
    protected boolean visible;
    protected Alignment alignment = Alignment.CENTER;
    protected final JsonObject extra = new JsonObject();

    //
    // Internal parameters
    //
    protected Layout parent;
    protected ViewLoader loader;
    protected int clientWidth, clientHeight;
    protected int x, y;

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

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setSize(String size) {
        val split = size.split(" ");
        width.loadFromValue(split[0]);
        height.loadFromValue(split[1]);
    }

    public void setClientSize(int width, int height) {
        clientWidth = width;
        clientHeight = height;
    }

    public void packToParent() {
        if (parent != null) {
            if (width.type == Dimension.Type.MATCH_PARENT)
                clientWidth = parent.clientWidth;
            else if (width.type == Dimension.Type.PERCENT)
                clientWidth = (int) (parent.clientWidth * width.percent / 100);
            if (height.type == Dimension.Type.MATCH_PARENT)
                clientHeight = parent.clientHeight;
            else if (height.type == Dimension.Type.PERCENT)
                clientHeight = (int) (parent.clientHeight * height.percent / 100);
        }
    }

    public void pack() {
        packToParent();
    }

    public void loadFromJson(JsonObject object) throws Exception {
        loader = ViewLoader.getLoader(object.get(String.class, "loader"));
        width.loadFromValue(object.get(String.class, "width"));
        height.loadFromValue(object.get(String.class, "height"));
        if (object.containsKey("size")) {
            val size = Objects.requireNonNull(object.get(String.class, "size")).split(" ");
            width.loadFromValue(size[0]);
            height.loadFromValue(size[1]);
        }
        padding.set(object.get(String.class, "padding"));
        margin.set(object.get(String.class, "margin"));
        id = JsonHelper.getOrDefault(object, "id", id);
        enabled = JsonHelper.getOrDefault(object, "enabled", enabled);
        visible = JsonHelper.getOrDefault(object, "visible", visible);
        alignment = Alignment.fromValue(JsonHelper.getOrDefault(object, "alignment", alignment.toString()));
        object.entrySet().stream().filter(e -> e.getKey().startsWith("#"))
                .forEach(e -> extra.put(e.getKey().substring(1), e.getValue()));
    }
}
