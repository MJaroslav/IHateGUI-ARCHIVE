package com.github.mjaroslav.ihategui.api.model.impl;

import blue.endless.jankson.JsonElement;
import blue.endless.jankson.JsonObject;
import com.github.mjaroslav.ihategui.api.model.Container;
import com.github.mjaroslav.ihategui.api.model.Node;
import com.github.mjaroslav.ihategui.api.model.Root;
import com.github.mjaroslav.ihategui.model.Alignment;
import com.github.mjaroslav.ihategui.model.Dimension;
import com.github.mjaroslav.ihategui.model.Sided;
import com.github.mjaroslav.ihategui.util.JsonHelper;
import com.github.mjaroslav.ihategui.view.ViewLoader;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.val;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@EqualsAndHashCode(exclude = {"loader", "parent", "totalWidth", "totalHeight",
        "contentX", "contentY", "root"})
@ToString(exclude = {"loader", "parent", "totalWidth", "totalHeight",
        "contentX", "contentY", "root"})
@Data
public abstract class Element implements Node {
    //
    // View parameters
    //
    @NotNull
    protected Dimension width = Dimension.of("match_parent");
    @NotNull
    protected Dimension height = Dimension.of("match_parent");
    @NotNull
    protected Sided padding = Sided.of("0");
    @NotNull
    protected Sided margin = Sided.of("0");
    @NotNull
    protected String id = "";
    protected boolean enabled = true;
    protected boolean visible = true;
    @NotNull
    protected Alignment alignment = Alignment.TOP_LEFT;
    protected int x, y;

    //
    // Internal parameters
    //
    protected final JsonObject extra = new JsonObject();

    @Nullable
    protected Container parent;
    protected Root root;

    @Nullable
    protected ViewLoader loader;
    protected int totalWidth, totalHeight;
    protected int contentWidth, contentHeight;
    protected int contentX, contentY;

    public void setTotalSize(int width, int height) {
        totalWidth = width;
        totalHeight = height;
    }

    @Override
    public void calculatePreferredSize() {
        if (parent != null) {
            if (width.isMatchingParent())
                totalWidth = parent.getTotalWidth();
            else if (width.isPercent())
                totalWidth = (int) (parent.getTotalWidth() * width.asPercent() / 100);
            else if (width.isConstant())
                totalWidth = width.asConstant();
            else
                totalWidth = contentWidth;

            if (height.isMatchingParent())
                totalHeight = parent.getTotalHeight();
            else if (height.isPercent())
                totalHeight = (int) (parent.getTotalHeight() * height.asPercent() / 100);
            else if (height.isConstant())
                totalHeight = height.asConstant();
            else
                totalHeight = contentHeight;
        } else {
            val root = getRoot();
            totalWidth = root.getRootWidth();
            totalHeight = root.getRootHeight();
        }
    }

    @Override
    public void setLoader(@NotNull ViewLoader loader) {
        this.loader = loader;
    }

    @Override
    public void pack() {
        calculateContentSize();
        calculatePreferredSize();
    }

    @Override
    public void setWidthStr(@NotNull String width) {
        setWidth(Dimension.of(width));
    }

    @Override
    public void setSize(@NotNull Dimension width, @NotNull Dimension height) {
        setWidth(width);
        setHeight(height);
    }

    @Override
    public void setSize(@NotNull String size) {
        val split = size.split(" ");
        setWidthStr(split[0]);
        setHeightStr(split[1]);
    }

    @Override
    public void setMarginStr(@NotNull String margin) {
        setMargin(Sided.of(margin));
    }

    @Override
    public void setPaddingStr(@NotNull String padding) {
        setPadding(Sided.of(padding));
    }

    @Override
    public void setAlignmentStr(@NotNull String value) {
        setAlignment(Alignment.of(value));
    }

    @Override
    public void setPosition(int x, int y) {
        setX(x);
        setY(y);
    }

    @Override
    public @NotNull Root getRoot() {
        return root != null ? root : parent == null ? (Root) this : parent.getRoot();
    }

    @Override
    public @Nullable Node findNodeById(@NotNull String id) {
        return getRoot().findNodeById(id);
    }

    @Override
    public void setHeightStr(@NotNull String height) {
        setHeight(Dimension.of(height));
    }

    @Override
    public void loadFromJson(@NotNull JsonObject object) throws Exception {
        setLoader(ViewLoader.getLoader(object.get(String.class, "loader")));

        val size = object.get(String.class, "size");
        if (size != null)
            setSize(size);
        else
            setSize("match_parent match_parent");

        val width = object.get(String.class, "width");
        if (width != null)
            setWidthStr(width);
        else if (size == null)
            setWidthStr("match_parent");

        val height = object.get(String.class, "height");
        if (height != null)
            setHeightStr(height);
        else if (size == null)
            setHeightStr("match_parent");

        val padding = object.get(String.class, "padding");
        if (padding != null)
            setPaddingStr(padding);
        else
            setPaddingStr("0");

        val margin = object.get(String.class, "margin");
        if (margin != null)
            setMarginStr(margin);
        else
            setMarginStr("0");

        setId(JsonHelper.getOrDefault(object, "id", ""));

        setEnabled(JsonHelper.getOrDefault(object, "enabled", true));

        setVisible(JsonHelper.getOrDefault(object, "visible", true));

        setAlignmentStr(JsonHelper.getOrDefault(object, "alignment", Alignment.TOP_LEFT.toString()));

        extra.clear();

        object.entrySet().stream().filter(e -> e.getKey().startsWith("#"))
                .forEach(e -> extra.put(e.getKey().substring(1), e.getValue()));
    }

    @Override
    public @NotNull JsonObject getExtraAttributes() {
        return extra;
    }

    @Override
    public void setExtraAttribute(@NotNull String key, @Nullable JsonElement value) {
        if (value == null)
            extra.remove(key);
        else
            extra.put(key, value);
    }

    @Override
    public @Nullable <T> T getExtraAttribute(@NotNull String key, Class<T> clazz) {
        return extra.get(clazz, key);
    }

    @Override
    public <T> @NotNull T getExtraAttribute(@NotNull String key, Class<T> type, T defaultObject) {
        val result = extra.get(type, key);
        return result == null ? defaultObject : result;
    }
}
