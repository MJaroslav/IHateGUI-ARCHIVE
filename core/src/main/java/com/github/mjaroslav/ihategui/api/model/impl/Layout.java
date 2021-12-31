package com.github.mjaroslav.ihategui.api.model.impl;

import blue.endless.jankson.JsonArray;
import blue.endless.jankson.JsonObject;
import com.github.mjaroslav.ihategui.api.model.Container;
import com.github.mjaroslav.ihategui.api.model.Node;
import com.github.mjaroslav.ihategui.view.ViewLoader;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.val;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public abstract class Layout extends Element implements Container {
    //
    // View parameters
    //
    protected final List<Node> nodes = new ArrayList<>();

    @Override
    public void addNode(@NotNull Node node) {
        node.setParent(this);
        val loader = getLoader();
        if (loader != null)
            node.setLoader(loader);
        nodes.add(node);
    }

    @Override
    public void setLoader(@NotNull ViewLoader loader) {
        this.loader = loader;
        nodes.forEach(e -> e.setLoader(loader));
    }

    @Override
    public void addNodes(Node... nodes) {
        for (val node : nodes)
            addNodes(node);
    }

    @Override
    public void addNodes(@NotNull Collection<Node> nodes) {
        nodes.forEach(this::addNode);
    }

    @Override
    public void loadFromJson(@NotNull JsonObject object) throws Exception {
        super.loadFromJson(object);
        nodes.clear();
        if (loader == null)
            throw new NullPointerException("Loader not found!");
        val nodes = object.get("nodes");
        if (nodes instanceof JsonArray) {
            val array = (JsonArray) nodes;
            for (val obj : array.stream().map(node -> (JsonObject) node).collect(Collectors.toList())) {
                Node node = loader.fromJson(this, obj);
                node.loadFromJson(obj);
                addNode(node);
            }
        }
    }
}
