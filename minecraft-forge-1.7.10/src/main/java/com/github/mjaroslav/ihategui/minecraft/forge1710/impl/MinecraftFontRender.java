package com.github.mjaroslav.ihategui.minecraft.forge1710.impl;

import com.github.mjaroslav.ihategui.api.model.TextSize;
import com.github.mjaroslav.ihategui.api.render.FontRender;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.minecraft.client.gui.FontRenderer;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public final class MinecraftFontRender implements FontRender {
    @Getter
    private int color;

    @Getter
    @Setter
    private int textSize = TextSize.DEFAULT_SIZE;

    @NonNull
    private final FontRenderer font;


    @Override
    public void drawLine(@NotNull String line, int x, int y) {
        font.drawString(line, x, y, color);
    }

    @Override
    public int getLineWidth(@NotNull String line) {
        return font.getStringWidth(line);
    }

    public void setColor(int rgb) {
        color = rgb;
    }

    @Override
    public void setColor(String hex) {
        setColor(Integer.parseInt(hex, 16));
    }

    @Override
    public void resetColor() {
        setColor(0xFFFFFF);
    }
}
