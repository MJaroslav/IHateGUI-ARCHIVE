package com.github.mjaroslav.ihategui.api.resource;

public interface Texture {
    void bind();

    void unbind();

    void load();

    void delete();

    boolean isLoaded();

    void reload();
}
