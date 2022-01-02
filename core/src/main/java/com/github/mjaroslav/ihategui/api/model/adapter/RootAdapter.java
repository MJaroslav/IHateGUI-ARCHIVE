package com.github.mjaroslav.ihategui.api.model.adapter;

import com.github.mjaroslav.ihategui.api.model.Container;
import com.github.mjaroslav.ihategui.api.model.Node;
import com.github.mjaroslav.ihategui.api.model.RootContainer;
import com.github.mjaroslav.ihategui.util.Pair.IntPair;
import lombok.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayDeque;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public abstract class RootAdapter implements RootContainer {
    @NotNull
    protected Container container;
    @NotNull
    protected IntPair rootSize = new IntPair();

    @Override
    public void setRootSize(int width, int height) {
        rootSize.set(width, height);
    }

    @Override
    public void setContainer(@NotNull Container container) {
        this.container = container;
        this.container.setRoot(this);
    }

    @Override
    public @Nullable Node findNodeById(@NotNull String id) {
        val deque = new ArrayDeque<>(container.getNodes());
        var node = (Node) null;
        while (!deque.isEmpty()) {
            node = deque.poll();
            if (id.equals(node.getId()))
                return node;
            if (node instanceof Container)
                deque.addAll(((Container) node).getNodes());
        }
        return null;
    }
}
