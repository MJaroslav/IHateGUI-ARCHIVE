package com.github.mjaroslav.ihategui.view;

import blue.endless.jankson.Jankson;
import blue.endless.jankson.api.SyntaxError;
import com.github.mjaroslav.ihategui.model.Layout;
import com.github.mjaroslav.ihategui.model.element.Button;
import com.github.mjaroslav.ihategui.model.layout.LinearLayout;
import com.github.mjaroslav.ihategui.util.ReflectionHelper;
import lombok.Getter;
import lombok.val;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ViewLoader {
    private static final Jankson JANKSON = Jankson.builder().build();

    private static final Map<String, Class<?>> builtInElements;

    @Getter
    protected Object controller;
    @Getter
    protected Layout container;

    public void load(InputStream layoutFileStream) {
        try {
            val obj = JANKSON.load(layoutFileStream);
            val containerType = obj.get(String.class, "class");
            container = (Layout) ReflectionHelper.createModelElement(containerType);
            container.loadFromJson(obj);
        } catch (IOException | ClassCastException | SyntaxError | NullPointerException e) {
            e.printStackTrace();
        }
    }

    public static Class<?> tryResolveElementByAlias(String alias) {
        return builtInElements.get(alias);
    }

    static {
        builtInElements = new HashMap<>();
        builtInElements.put("LinearLayout", LinearLayout.class);
        builtInElements.put("Button", Button.class);
    }
}
