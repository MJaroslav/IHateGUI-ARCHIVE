package com.github.mjaroslav.ihategui.view;

import blue.endless.jankson.Jankson;
import blue.endless.jankson.JsonArray;
import blue.endless.jankson.JsonObject;
import blue.endless.jankson.JsonPrimitive;
import com.github.mjaroslav.ihategui.model.Element;
import com.github.mjaroslav.ihategui.model.Layout;
import com.github.mjaroslav.ihategui.util.ReflectionHelper;
import lombok.Getter;
import lombok.val;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ViewLoader {
    private static final Jankson JANKSON = Jankson.builder().build();

    private static final Map<String, ViewLoader> loaders = new HashMap<>();

    @Getter
    protected final String id;

    public ViewLoader() {
        this(System.currentTimeMillis() + "");
    }

    public ViewLoader(String id) {
        this.id = id;
        loaders.put(id, this);
    }

    @Getter
    protected Object controller;
    @Getter
    protected Layout container;
    @Getter
    protected final Map<String, Class<?>> imported = new HashMap<>();

    public void load(InputStream layoutFileStream) throws Exception {
        val obj = JANKSON.load(layoutFileStream);
        if (obj.containsKey("imports")) {
            val imports = (JsonArray) obj.get("imports");
            for (val element : Objects.requireNonNull(imports)) {
                val importLine = ((JsonPrimitive) element).asString();
                importToDeserializer(importLine);
            }
        }
        obj.remove("imports");
        if (obj.containsKey("controller")) {
            controller = ReflectionHelper.createClassInstance(obj.get(String.class, "controller"));
            obj.remove("controller");
        }
        container = (Layout) fromJson(obj);
    }

    public void importToDeserializer(String importLine) throws Exception {
        if (importLine.indexOf(' ') > 0) {
            val pronoun = importLine.substring(importLine.indexOf(' ') + 1);
            val clazz = Class.forName(importLine.substring(0, importLine.indexOf(' ')));
            if (imported.containsKey(pronoun))
                throw new IllegalArgumentException(
                        String.format("This name \"%s\" is taken by \"%s\"! add a pronoun separated by a space"
                                , pronoun, imported.get(pronoun).getName()));
            imported.put(pronoun, clazz);
        } else if (importLine.endsWith("*")) {
            val classes = ReflectionHelper.getClassesInPackage(importLine.substring(0, importLine.length() - 2), false);
            for (Class<?> clazz : classes) {
                val pronoun = clazz.getSimpleName();
                if (imported.containsKey(pronoun))
                    throw new IllegalArgumentException(
                            String.format("This name \"%s\" is taken by \"%s\"! add a pronoun separated by a space"
                                    , pronoun, imported.get(pronoun).getName()));
                imported.put(pronoun, clazz);
            }
        } else {
            val clazz = Class.forName(importLine);
            val pronoun = clazz.getSimpleName();
            if (imported.containsKey(pronoun))
                throw new IllegalArgumentException(
                        String.format("This name \"%s\" is taken by \"%s\"! add a pronoun separated by a space"
                                , pronoun, imported.get(pronoun).getName()));
            imported.put(pronoun, clazz);
        }
    }

    public Element fromJson(JsonObject object) throws Exception {
        val clazz = imported.get(object.get(String.class, "class"));
        object.remove("class");
        object.put("loader", new JsonPrimitive(id));
        return (Element) JANKSON.fromJsonCarefully(object, clazz);
    }

    public static ViewLoader getLoader(String id) {
        return loaders.get(id);
    }
}
