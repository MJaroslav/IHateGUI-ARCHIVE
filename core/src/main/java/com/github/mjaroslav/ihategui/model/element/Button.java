package com.github.mjaroslav.ihategui.model.element;

import blue.endless.jankson.JsonObject;
import blue.endless.jankson.annotation.Deserializer;
import com.github.mjaroslav.ihategui.model.Element;
import com.github.mjaroslav.ihategui.util.JsonHelper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.val;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class Button extends Element {
    protected String text = "";

    @Override
    public void loadFromJson(JsonObject object) throws Exception {
        super.loadFromJson(object);
        text = JsonHelper.getOrDefault(object, "text", text);
    }

    @Deserializer
    public static Button deserialize(JsonObject object) throws Exception {
        val result = new Button();
        result.loadFromJson(object);
        return result;
    }
}
