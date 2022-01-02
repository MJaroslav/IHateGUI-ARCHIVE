package com.github.mjaroslav.ihategui.api.model;

import com.github.mjaroslav.ihategui.api.render.ViewRender;
import com.github.mjaroslav.ihategui.util.Pair.IntPair;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public interface Node {
    // =========================
    // Node properties
    // =========================
    @Nullable
    String getId();

    void setId(@Nullable String id);

    boolean isEnable();

    void setEnable(boolean enable);

    boolean isVisible();

    void setVisible(boolean visible);

    // =========================
    // Node position
    // =========================
    @NotNull
    IntPair getPosition();

    void setPosition(int x, int y);

    void setPosition(@NotNull IntPair position);

    // =========================
    // Node size
    // =========================
    @NotNull
    IntPair getSize();

    void setSize(int width, int height);

    void setSize(@NotNull IntPair size);

    // =========================
    // Technical stuff
    // =========================
    @Nullable
    Container getParent();

    void setParent(@NotNull Container parent);

    @Nullable
    RootContainer getRoot();

    void setRoot(@Nullable RootContainer root);

    boolean isDirty();

    void setDirty(boolean dirty);

    // =========================
    // Attributes
    // =========================
    @NotNull
    Map<String, Object> getAttributes();

    void setAttribute(@NotNull String key, @NotNull Object value);

    void deleteAttribute(@NotNull String key);

    @Nullable <T> T getAttribute(@NotNull String key);

    @NotNull <T> T getAttribute(@NotNull String key, T defaultValue);

    // =========================
    // Graphics
    // =========================
    void draw(ViewRender render, int mouseX, int mouseY, float partialTicks);

    // Required for builders
    static void setAllParams(@NotNull Node node, @Nullable IntPair size,
                             @Nullable IntPair position, boolean enable, boolean visible,
                             @Nullable String id, @Nullable Container parent, @Nullable RootContainer root,
                             @Nullable Map<String, Object> attributes) {
        if (size != null)
            node.setSize(size);
        if (position != null)
            node.setPosition(position);
        node.setEnable(enable);
        node.setVisible(visible);
        node.setId(id);
        if (parent != null)
            node.setParent(parent);
        if (root != null)
            node.setRoot(root);
        if (attributes != null)
            node.getAttributes().putAll(attributes);
    }

    static AttributeBuilder attribBuilder() {
        return new AttributeBuilder();
    }

    @NoArgsConstructor(access = AccessLevel.PACKAGE)
    final class AttributeBuilder {
        private final Map<String, Object> attributes = new HashMap<>();

        public AttributeBuilder setAttribute(String key, Object value) {
            attributes.put(key, value);
            return this;
        }

        public Map<String, Object> build() {
            return attributes;
        }
    }
}
