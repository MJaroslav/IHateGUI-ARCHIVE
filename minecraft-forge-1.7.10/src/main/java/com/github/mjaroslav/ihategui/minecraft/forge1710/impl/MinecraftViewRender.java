package com.github.mjaroslav.ihategui.minecraft.forge1710.impl;

import com.github.mjaroslav.ihategui.api.model.Node;
import com.github.mjaroslav.ihategui.api.render.CustomViewRenderer;
import com.github.mjaroslav.ihategui.api.render.FontRender;
import com.github.mjaroslav.ihategui.api.render.ViewRender;
import com.github.mjaroslav.ihategui.api.resource.Resource;
import com.github.mjaroslav.ihategui.api.resource.Texture;
import com.github.mjaroslav.ihategui.util.Pair;
import lombok.val;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;
import java.util.Map;

public final class MinecraftViewRender implements ViewRender {
    private final Map<Class<? extends Node>, CustomViewRenderer> customRenderers = new HashMap<>();

    private Texture lastBoundTexture;

    private FontRender fontRender;

    @Override
    public void setCustomNodeRenderer(@NotNull Class<? extends Node> nodeClass, @NotNull CustomViewRenderer renderer) {
        customRenderers.put(nodeClass, renderer);
    }

    @Nullable
    @Override
    public CustomViewRenderer getCustomNodeRenderer(@NotNull Class<? extends Node> nodeClass) {
        return customRenderers.get(nodeClass);
    }

    @Override
    public void setColor(int rgb, float alpha) {
        val r = ((rgb >> 16) & 0xFF) / 256f;
        val g = ((rgb >> 8) & 0xFF) / 256f;
        val b = (rgb & 0xFF) / 256f;
        GL11.glColor4f(r, g, b, alpha);
    }

    @Override
    public void setColor(@NotNull String hex, float alpha) {
        setColor(Integer.parseInt(hex, 16), alpha);
    }

    @Override
    public void resetColor() {
        GL11.glColor4f(1, 1, 1, 1);
    }

    @Override
    public @NotNull Texture loadTexture(@NotNull Resource resource) {
        return new MinecraftTexture(((MinecraftResource) resource).getLocation());
    }

    @Override
    public void bindTexture(@NotNull Texture texture) {
        texture.bind();
        lastBoundTexture = texture;
    }

    @Override
    public void unbindTexture(@NotNull Texture texture) {
        texture.unbind();
        if (texture == lastBoundTexture)
            lastBoundTexture = null;
    }

    @Override
    public @Nullable Texture getBoundTexture() {
        return lastBoundTexture;
    }

    @Override
    public void unbindAllTextures() {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        lastBoundTexture = null;
    }

    @Override
    public void cropRenderToSize(Pair.@NotNull IntPair position, Pair.@NotNull IntPair size) {

    }

    @Override
    public void cropRenderToSize(int x, int y, int width, int height) {

    }

    @Override
    public void stopRenderCropping() {

    }

    @Override
    public void drawRect(Pair.@NotNull IntPair position, Pair.@NotNull IntPair size) {

    }

    @Override
    public void drawRect(int x, int y, int width, int height) {

    }

    @Override
    public void drawRectWithUV(Pair.@NotNull IntPair position, Pair.@NotNull IntPair size, Pair.@NotNull DoublePair uvPosition, Pair.@NotNull DoublePair uvSize) {

    }

    @Override
    public @NotNull FontRender getFontRender() {
        if (fontRender == null)
            setFontRender(new MinecraftFontRender(Minecraft.getMinecraft().fontRenderer));
        return fontRender;
    }

    @Override
    public void setFontRender(@NotNull FontRender render) {
        fontRender = render;
    }
}
