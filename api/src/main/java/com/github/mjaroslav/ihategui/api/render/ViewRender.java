package com.github.mjaroslav.ihategui.api.render;

import com.github.mjaroslav.ihategui.api.model.Node;
import com.github.mjaroslav.ihategui.api.resource.Resource;
import com.github.mjaroslav.ihategui.api.resource.Texture;
import com.github.mjaroslav.ihategui.util.Pair.DoublePair;
import com.github.mjaroslav.ihategui.util.Pair.IntPair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ViewRender {
    void setCustomNodeRenderer(@NotNull Class<? extends Node> nodeClass, @NotNull CustomViewRenderer renderer);

    void setColor(int rgb, float alpha);

    @Nullable
    CustomViewRenderer getCustomNodeRenderer(@NotNull Class<? extends Node> nodeClass);

    void setColor(@NotNull String hex, float alpha);

    void resetColor();

    @NotNull
    Texture loadTexture(@NotNull Resource resource);

    void bindTexture(@NotNull Texture texture);

    void unbindTexture(@NotNull Texture texture);

    @Nullable
    Texture getBoundTexture();

    void unbindAllTextures();

    void cropRenderToSize(@NotNull IntPair position, @NotNull IntPair size);

    void cropRenderToSize(int x, int y, int width, int height);

    void stopRenderCropping();

    void drawRect(@NotNull IntPair position, @NotNull IntPair size);

    void drawRect(int x, int y, int width, int height);

    void drawRectWithUV(@NotNull IntPair position, @NotNull IntPair size, @NotNull DoublePair uvPosition, @NotNull DoublePair uvSize);

    @NotNull
    FontRender getFontRender();

    void setFontRender(@NotNull FontRender render);
}
