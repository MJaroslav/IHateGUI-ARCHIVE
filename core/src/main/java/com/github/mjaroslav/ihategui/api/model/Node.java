package com.github.mjaroslav.ihategui.api.model;

import blue.endless.jankson.JsonElement;
import blue.endless.jankson.JsonObject;
import com.github.mjaroslav.ihategui.model.Alignment;
import com.github.mjaroslav.ihategui.model.Dimension;
import com.github.mjaroslav.ihategui.model.Sided;
import com.github.mjaroslav.ihategui.view.ViewLoader;
import lombok.val;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Node {
    void pack();

    void calculatePreferredSize();

    void calculateContentSize();

    void loadFromJson(@NotNull JsonObject object) throws Exception;

    void setWidth(@NotNull Dimension width);

    @NotNull
    Dimension getWidth();

    void setWidthStr(@NotNull String width);

    void setHeight(@NotNull Dimension height);

    @NotNull
    Dimension getHeight();

    void setHeightStr(@NotNull String height);

    void setSize(@NotNull Dimension width, @NotNull Dimension height);

    void setSize(@NotNull String size);

    void setMarginStr(@NotNull String margin);

    void setMargin(@NotNull Sided margin);

    @NotNull
    Sided getMargin();

    void setPaddingStr(@NotNull String padding);

    void setPadding(@NotNull Sided padding);

    @NotNull
    Sided getPadding();

    @Nullable
    String getId();

    void setId(@Nullable String id);

    boolean isEnabled();

    void setEnabled(boolean enabled);

    boolean isVisible();

    void setVisible(boolean visible);

    @NotNull
    Alignment getAlignment();

    void setAlignment(@NotNull Alignment alignment);

    void setAlignmentStr(@NotNull String value);

    int getX();

    int getY();

    void setX(int x);

    void setY(int y);

    void setPosition(int x, int y);

    int getTotalWidth();

    void setTotalWidth(int totalWidth);

    int getTotalHeight();

    void setTotalHeight(int totalHeight);

    int getContentWidth();

    void setContentWidth(int contentWidth);

    int getContentHeight();

    void setContentHeight(int contentHeight);

    @Nullable
    Container getParent();

    void setParent(@NotNull Container parent);

    ViewLoader getLoader();

    void setLoader(@NotNull ViewLoader loader);

    @NotNull
    Root getRoot();

    void setRoot(@NotNull Root root);

    @Nullable
    Node findNodeById(@NotNull String id);

    @NotNull
    JsonObject getExtraAttributes();

    void setExtraAttribute(@NotNull String key, @Nullable JsonElement value);

    @Nullable <T> T getExtraAttribute(@NotNull String key, Class<T> type);

    @NotNull <T> T getExtraAttribute(@NotNull String key, Class<T> type, T defaultObject);
}
