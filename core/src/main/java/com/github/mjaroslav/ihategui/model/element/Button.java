package com.github.mjaroslav.ihategui.model.element;

import blue.endless.jankson.JsonObject;
import com.github.mjaroslav.ihategui.model.Element;
import com.github.mjaroslav.ihategui.util.JsonHelper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class Button extends Element {
    protected String text = "";

    @Override
    public void loadFromJson(JsonObject object) {
        super.loadFromJson(object);
        text = JsonHelper.getOrDefault(object, "text", text);
    }
}
