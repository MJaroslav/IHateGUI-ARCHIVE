package com.github.mjaroslav.ihategui.api.model.impl;

import com.github.mjaroslav.ihategui.api.model.Container;
import com.github.mjaroslav.ihategui.api.model.Node;
import com.github.mjaroslav.ihategui.api.model.Root;
import com.github.mjaroslav.ihategui.view.ViewLoader;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.val;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayDeque;

//@EqualsAndHashCode(exclude = "rootLoader", callSuper = true)
@ToString(exclude = "rootLoader")
@EqualsAndHashCode(exclude = "rootLoader")
@Data
public class RootAdapter implements Root {
    protected int rootWidth, rootHeight;
    @Nullable
    protected ViewLoader rootLoader;
    @Nullable
    protected Container rootContainer;
    @Nullable
    protected Object controller;

    @Override
    public void setRootContainerStr(@NotNull String resourcePath) throws Exception {
        if (rootLoader == null)
            throw new NullPointerException("ViewLoader is null!");
        // TODO: Make resource loader
        if (!resourcePath.startsWith("/"))
            resourcePath = "/" + resourcePath;
        rootLoader.load(RootAdapter.class.getResourceAsStream(resourcePath));
        rootLoader.getContainer().setRoot(this);
        setRootContainer(rootLoader.getContainer());
        setController(rootLoader.getController());
    }

//    @Override
//    public void calculateContentSize() {
//        contentWidth = getRootWidth();
//        contentHeight = getRootHeight();
//    }
//
//    @Override
//    public void calculatePreferredSize() {
//        totalWidth = getRootWidth();
//        totalHeight = getRootHeight();
//    }

    @Nullable
    @Override
    public Node findNodeById(@NotNull String id) {
        if (rootContainer == null)
            return null;
        val deque = new ArrayDeque<>(rootContainer.getNodes());
        while (!deque.isEmpty()) {
            val node = deque.poll();
            if (id.equals(node.getId()))
                return node;
            else if (node instanceof Container)
                deque.addAll(((Container) node).getNodes());
        }
        return null;
    }

    @Override
    public void pack() {
        if (rootContainer != null) {
            rootContainer.pack();
        }
    }
}
