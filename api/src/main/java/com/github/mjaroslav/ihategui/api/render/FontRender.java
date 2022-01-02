package com.github.mjaroslav.ihategui.api.render;

import org.jetbrains.annotations.NotNull;

public interface FontRender {
    void drawLine(@NotNull String line, int x, int y);

    int getLineWidth(@NotNull String line);

    void setColor(int rgb);

    void setColor(String hex);

    void resetColor();

    void setTextSize(int textSize);

    int getTextSize();
}
