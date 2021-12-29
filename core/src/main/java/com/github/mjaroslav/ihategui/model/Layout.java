package com.github.mjaroslav.ihategui.model;

import blue.endless.jankson.JsonArray;
import blue.endless.jankson.JsonElement;
import blue.endless.jankson.JsonObject;
import com.github.mjaroslav.ihategui.util.ReflectionHelper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public abstract class Layout extends Element {
    protected Object controller;
    protected final List<Element> elements = new ArrayList<>();

    @Override
    public void loadFromJson(JsonObject object) {
        super.loadFromJson(object);
        if (object.containsKey("controller"))
            controller = ReflectionHelper.createClassInstance(object.get(String.class, "controller"));
        JsonElement elements = object.get("elements");
        if (elements instanceof JsonArray) {
            JsonArray array = (JsonArray) elements;
            array.forEach(arrayElement -> {
                JsonObject elementObject = (JsonObject) arrayElement;
                Element element = (Element) ReflectionHelper.createClassInstance(elementObject.get(String.class, "class"));
                if (element != null) {
                    element.loadFromJson(elementObject);
                    this.elements.add(element);
                }
            });
        }
    }
}
