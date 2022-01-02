package com.github.mjaroslav.ihategui.api.model.adapter;

import com.github.mjaroslav.ihategui.api.model.Container;
import com.github.mjaroslav.ihategui.api.model.Node;
import com.github.mjaroslav.ihategui.api.model.RootContainer;
import com.github.mjaroslav.ihategui.api.view.Ignore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@EqualsAndHashCode(callSuper = true, of = "nodes")
@ToString(callSuper = true, of = "nodes")
@Getter
@Setter
public abstract class ContainerAdapter extends NodeAdapter implements Container {
    @Ignore
    protected final List<Node> nodes = new ArrayList<>();

    @Override
    public void addNode(@NotNull Node node) {
        node.setParent(this);
        node.setRoot(root);
        nodes.add(node);
    }

    @Override
    public void addNodes(@NotNull Node... nodes) {
        for (Node node : nodes)
            addNodes(node);
    }

    @Override
    public void addNodes(@NotNull Collection<Node> nodes) {
        nodes.forEach(this::addNode);
    }

    @Override
    public void setRoot(@Nullable RootContainer root) {
        super.setRoot(root);
        nodes.forEach(node -> node.setRoot(root));
    }
}
