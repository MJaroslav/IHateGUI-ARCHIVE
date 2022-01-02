package com.github.mjaroslav.ihategui.api.render;

import com.github.mjaroslav.ihategui.api.model.Node;

@FunctionalInterface
public interface CustomViewRenderer {
    void draw(ViewRender render, Node node, int mouseX, int mouseY, float partialTicks);
}
