package com.github.mjaroslav.ihategui.view;

import blue.endless.jankson.Jankson;
import blue.endless.jankson.JsonObject;
import blue.endless.jankson.api.SyntaxError;
import com.github.mjaroslav.ihategui.model.Layout;
import com.github.mjaroslav.ihategui.util.ReflectionHelper;
import lombok.Getter;

import java.io.IOException;
import java.io.InputStream;

public class ViewLoader {
    public static final Jankson JANKSON = Jankson.builder().build();

    @Getter
    protected Object controller;
    @Getter
    protected Layout container;

    public void load(InputStream layoutFileStream) {
        try {
            JsonObject obj = JANKSON.load(layoutFileStream);
            String containerType = obj.get(String.class, "class");
            container = (Layout) ReflectionHelper.createClassInstance(containerType);
            container.loadFromJson(obj);
        } catch (IOException | ClassCastException | SyntaxError | NullPointerException e) {
            e.printStackTrace();
        }
    }
}
