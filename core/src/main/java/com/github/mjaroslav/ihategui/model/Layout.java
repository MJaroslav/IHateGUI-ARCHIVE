package com.github.mjaroslav.ihategui.model;

import blue.endless.jankson.JsonArray;
import blue.endless.jankson.JsonElement;
import blue.endless.jankson.JsonObject;
import com.github.mjaroslav.ihategui.util.ReflectionHelper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public abstract class Layout extends Element {
    //
    // View parameters
    //
    protected Object controller;
    protected final List<Element> elements = new ArrayList<>();

    public void addElement(Element element) {
        element.parent = this;
        elements.add(element);
    }

    public void addElements(Element... elements) {
        for (Element element : elements) {
            element.parent = this;
            this.elements.add(element);
        }
    }

    public void addElements(Collection<Element> elements) {
        elements.stream().map(e -> e.parent = this).forEach(elements::add);
    }

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
                Element element = (Element) ReflectionHelper.createModelElement(elementObject.get(String.class, "class"));
                if (element != null) {
                    element.loadFromJson(elementObject);
                    element.setParent(this);
                    this.elements.add(element);
                }
            });
        }
    }

    @Override
    public void pack() {
        super.pack();
        elements.forEach(Element::packToParent);
        elements.forEach(Element::pack);
    }
}
