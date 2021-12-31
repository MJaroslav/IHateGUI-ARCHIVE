package com.github.mjaroslav.ihategui.model;

import blue.endless.jankson.JsonArray;
import blue.endless.jankson.JsonObject;
import com.github.mjaroslav.ihategui.view.ViewLoader;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.val;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public abstract class Layout extends Element {
    //
    // View parameters
    //
    protected List<Element> elements = new ArrayList<>();

    public void addElement(Element element) {
        element.parent = this;
        element.loader = loader;
        elements.add(element);
    }

    public void setLoader(ViewLoader loader) {
        this.loader = loader;
        elements.forEach(e -> e.setLoader(loader));
    }

    public void addElements(Element... elements) {
        for (Element element : elements) {
            element.parent = this;
            element.loader = loader;
            this.elements.add(element);
        }
    }

    public void addElements(Collection<Element> elements) {
        elements.stream().peek(e -> {
            e.parent = this;
            e.loader = loader;
        }).forEach(elements::add);
    }

    @SuppressWarnings("unchecked")
    public <T extends Element> T findElementById(String id) {
        if (id != null && id.length() > 0) {
            for (val element : elements) {
                if (id.equals(element.id))
                    return (T) element;
                if (element instanceof Layout) {
                    val rec = ((Layout) element).findElementById(id);
                    if (rec != null)
                        return (T) rec;
                }
            }
        }
        return null;
    }

    @Override
    public void loadFromJson(JsonObject object) throws Exception {
        super.loadFromJson(object);
        val elements = object.get("elements");
        if (elements instanceof JsonArray) {
            val array = (JsonArray) elements;
            for (val obj : array.stream().map(e -> (JsonObject) e).collect(Collectors.toList())) {
                Element element = loader.fromJson(obj);
                element.loadFromJson(obj);
                addElement(element);
            }
        }

    }

    @Override
    public void pack() {
        super.pack();
        elements.forEach(Element::packToParent);
        elements.forEach(Element::pack);
    }
}
