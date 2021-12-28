package com.github.mjaroslav.ihategui.view;

import blue.endless.jankson.Jankson;
import blue.endless.jankson.JsonObject;
import blue.endless.jankson.api.SyntaxError;
import com.github.mjaroslav.ihategui.model.Layout;
import com.github.mjaroslav.ihategui.util.ReflectionHelper;

import java.io.IOException;
import java.io.InputStream;

public class ViewLoader {
    public static final Jankson JANKSON = Jankson.builder().build();

    public static final String KEY_TYPE = "type";
    public static final String KEY_CONTROLLER = "controller";

    protected Object controller;
    protected Layout container;

    public void load(InputStream layoutFileStream) {
        try {
            JsonObject obj = JANKSON.load(layoutFileStream);
            String containerType = obj.get(String.class, KEY_TYPE);
            container = (Layout) ReflectionHelper.createClassInstance(containerType);
        } catch (IOException | ClassCastException | SyntaxError | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
