package com.github.mjaroslav.ihategui.model.layout;

import blue.endless.jankson.JsonObject;
import blue.endless.jankson.JsonPrimitive;
import blue.endless.jankson.annotation.Deserializer;
import com.github.mjaroslav.ihategui.model.Alignment;
import com.github.mjaroslav.ihategui.model.Element;
import com.github.mjaroslav.ihategui.model.Layout;
import com.github.mjaroslav.ihategui.model.Orientation;
import com.github.mjaroslav.ihategui.util.JsonHelper;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class LinearLayout extends Layout {
    protected Orientation orientation = Orientation.HORIZONTAL;

    @Override
    public void loadFromJson(JsonObject object) throws Exception {
        super.loadFromJson(object);
        orientation = Orientation.fromName(JsonHelper.getOrDefault(object, "orientation", orientation.toString()));
    }

    @Deserializer
    public static LinearLayout deserialize(JsonObject object) throws Exception {
        val result = new LinearLayout();
        result.loadFromJson(object);
        return result;
    }

    @Override
    public void pack() {
        super.pack();
        if (orientation == Orientation.HORIZONTAL) {
            var oneWeightWidth = (getClientWidth() - elements.stream()
                    .filter(e -> !isElementHasWeight(e))
                    .mapToInt(Element::getClientWidth).sum()) /
                    elements.stream().filter(LinearLayout::isElementHasWeight)
                            .mapToInt(LinearLayout::getElementWeight).sum();
            elements.stream().filter(LinearLayout::isElementHasWeight)
                    .forEach(e -> e.setClientWidth(oneWeightWidth * getElementWeight(e)));
            var offset = 0;
            for (val e : elements) {
                e.setX(offset);
                offset += e.getClientWidth();
                if (alignment == Alignment.TOP)
                    e.setY(0);
                else if (alignment == Alignment.BOTTOM)
                    e.setY(getClientHeight() - e.getClientHeight());
                else if (alignment == Alignment.CENTER)
                    e.setY((getClientHeight() - e.getClientHeight()) / 2);
            }
        } else {
            var oneWeightHeight = (getClientHeight() - elements.stream()
                    .filter(e -> !isElementHasWeight(e))
                    .mapToInt(Element::getClientHeight).sum()) /
                    elements.stream().filter(LinearLayout::isElementHasWeight)
                            .mapToInt(LinearLayout::getElementWeight).sum();
            elements.stream().filter(LinearLayout::isElementHasWeight)
                    .forEach(e -> e.setClientHeight(oneWeightHeight * getElementWeight(e)));
            var offset = 0;
            for (val e : elements) {
                e.setY(offset);
                offset += e.getClientHeight();
                if (alignment == Alignment.TOP)
                    e.setX(0);
                else if (alignment == Alignment.BOTTOM)
                    e.setX(getClientWidth() - e.getClientWidth());
                else if (alignment == Alignment.CENTER)
                    e.setX((getClientWidth() - e.getClientWidth()) / 2);
            }
        }
    }

    public static boolean isElementHasWeight(Element element) {
        return getElementWeight(element) > 0;
    }

    public static int getElementWeight(Element element) {
        return element.getExtra().getInt("linearlayout_weight", 0);
    }

    public static void setElementWeight(Element element, int value) {
        element.getExtra().put("linearlayout_weight", new JsonPrimitive(value));
    }
}
