package com.github.mjaroslav.ihategui.minecraft.forge1710.impl;

import com.github.mjaroslav.ihategui.api.resource.Resource;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;

@RequiredArgsConstructor
public class MinecraftResource implements Resource {
    @NotNull
    @Getter
    private final ResourceLocation location;

    @Override
    public @NotNull InputStream load() throws IOException {
        // What is it?
        return Minecraft.getMinecraft().getResourcePackRepository().func_148530_e().getInputStream(location);
    }
}
