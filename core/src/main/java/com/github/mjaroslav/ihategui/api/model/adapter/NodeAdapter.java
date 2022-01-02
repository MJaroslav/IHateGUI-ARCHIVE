package com.github.mjaroslav.ihategui.api.model.adapter;

import com.github.mjaroslav.ihategui.api.model.Container;
import com.github.mjaroslav.ihategui.api.model.Node;
import com.github.mjaroslav.ihategui.api.model.RootContainer;
import com.github.mjaroslav.ihategui.api.view.Ignore;
import com.github.mjaroslav.ihategui.util.Pair.IntPair;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@ToString(of = {"size", "position", "visible", "enable", "id", "attributes"})
@EqualsAndHashCode(of = {"size", "position", "visible", "enable", "id", "attributes"})
public abstract class NodeAdapter implements Node {
    // =========================
    // Should be deserialized
    // =========================
    @NotNull
    protected IntPair size = new IntPair();
    @NotNull
    protected IntPair position = new IntPair();
    protected boolean visible, enable;
    @Nullable
    protected String id;

    // =========================
    // Technical stuff
    // =========================
    protected final Map<String, Object> attributes = new HashMap<>();

    @Nullable
    @Ignore
    protected Container parent;
    @Nullable
    @Ignore
    protected RootContainer root;
    @Ignore
    protected boolean dirty = true;

    @Override
    public void setPosition(int x, int y) {
        position.set(x, y);
    }

    @Override
    public void setSize(int width, int height) {
        size.set(width, height);
    }

    @Override
    public void deleteAttribute(@NotNull String key) {
        attributes.remove(key);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> @Nullable T getAttribute(@NotNull String key) {
        return (T) attributes.get(key);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> @NotNull T getAttribute(@NotNull String key, T defaultValue) {
        return (T) attributes.getOrDefault(key, defaultValue);
    }

    @Override
    public void setAttribute(@NotNull String key, @NotNull Object value) {
        attributes.put(key, value);
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {

    }
}
