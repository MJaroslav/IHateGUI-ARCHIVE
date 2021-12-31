package com.github.mjaroslav.ihategui.api.model;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

public interface Container extends Node {
    @NotNull
    List<Node> getNodes();

    void addNode(@NotNull Node node);

    void addNodes(@NotNull Node... nodes);

    void addNodes(@NotNull Collection<Node> nodes);
}
