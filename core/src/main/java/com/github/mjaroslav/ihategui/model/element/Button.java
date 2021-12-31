package com.github.mjaroslav.ihategui.model.element;

import blue.endless.jankson.JsonObject;
import blue.endless.jankson.annotation.Deserializer;
import com.github.mjaroslav.ihategui.model.Alignment;
import com.github.mjaroslav.ihategui.api.model.impl.Element;
import com.github.mjaroslav.ihategui.model.TextSize;
import com.github.mjaroslav.ihategui.util.JsonHelper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.val;
import org.jetbrains.annotations.NotNull;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class Button extends Element {
    @NotNull
    protected String text = "";
    @NotNull
    protected TextSize textSize = TextSize.of("default");
    @NotNull
    protected Alignment textAlignment = Alignment.CENTER;

    public void setTextSizeStr(@NotNull String textSize) {
        setTextSize(TextSize.of(textSize));
    }

    public void setTextAlignmentStr(@NotNull String textAlignment) {
        setTextAlignment(Alignment.of(textAlignment));
    }

    @Override
    public void pack() {

    }

    @Override
    public void calculateContentSize() {

    }

    @Override
    public void loadFromJson(@NotNull JsonObject object) throws Exception {
        super.loadFromJson(object);
        setText(JsonHelper.getOrDefault(object, "text", ""));
        val textSize = object.get(String.class, "text_size");
        if (textSize != null)
            setTextSizeStr(textSize);
        setTextAlignment(JsonHelper.getOrDefault(object, "text_alignment", Alignment.CENTER));
    }

    @Deserializer
    public static Button deserialize(JsonObject object) throws Exception {
        val result = new Button();
        result.loadFromJson(object);
        return result;
    }
}
