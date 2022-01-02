package com.github.mjaroslav.ihategui.minecraft.forge1710.impl;

import com.github.mjaroslav.ihategui.api.resource.Texture;
import lombok.RequiredArgsConstructor;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@RequiredArgsConstructor
public class MinecraftTexture implements Texture {
    private final ResourceLocation location;
    private boolean loaded;

    @Override
    public void bind() {
        Minecraft.getMinecraft().getTextureManager().bindTexture(location);
        loaded = true;
    }

    @Override
    public void unbind() {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
    }

    @Override
    public void load() {
        loaded = true;
    }

    @Override
    public void delete() {
        Minecraft.getMinecraft().getTextureManager().deleteTexture(location);
        loaded = false;
    }

    @Override
    public boolean isLoaded() {
        return loaded;
    }

    @Override
    public void reload() {
        delete();
        loaded = true;
    }
}
