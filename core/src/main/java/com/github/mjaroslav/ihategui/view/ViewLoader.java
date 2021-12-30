package com.github.mjaroslav.ihategui.view;

import blue.endless.jankson.Jankson;
import blue.endless.jankson.JsonObject;
import blue.endless.jankson.api.DeserializationException;
import com.github.mjaroslav.ihategui.model.Element;
import com.github.mjaroslav.ihategui.model.Layout;
import com.github.mjaroslav.ihategui.util.ReflectionHelper;
import lombok.Getter;
import lombok.val;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ViewLoader {
    private static final Jankson JANKSON = Jankson.builder().build();

    @Getter
    protected Object controller;
    @Getter
    protected Layout container;

    protected final Map<String, Class<?>> imported = new HashMap<>();

    public void load(InputStream layoutFileStream) throws Exception {
        val obj = JANKSON.load(layoutFileStream);
        container = (Layout) fromJson(obj);
        if (obj.containsKey("controller"))
            controller = ReflectionHelper.createClassInstance(obj.get(String.class, "controller"));
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
            val pakage = Package.getPackage(importLine.substring(0, importLine.length() - 2));

        } else {

        }
    }

    public Element fromJson(JsonObject object) throws ClassNotFoundException, DeserializationException, ClassCastException {
        val clazz = getClass(object.get(String.class, "class"));
        return (Element) JANKSON.fromJsonCarefully(object, clazz);
    }

    public Class<?> tryResolveElementByAlias(String alias) {
        return null;
    }

    public Class<?> getClass(String className) throws ClassNotFoundException {
        val clazz = tryResolveElementByAlias(className);
        return clazz == null ? Class.forName(className) : clazz;
    }
}
