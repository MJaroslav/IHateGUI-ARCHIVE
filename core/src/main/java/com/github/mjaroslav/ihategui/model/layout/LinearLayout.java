package com.github.mjaroslav.ihategui.model.layout;

import blue.endless.jankson.JsonObject;
import blue.endless.jankson.JsonPrimitive;
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
    public void loadFromJson(JsonObject object) {
        super.loadFromJson(object);
        orientation = Orientation.fromName(JsonHelper.getOrDefault(object, "orientation", orientation.toString()));
    }

    @Override
    public void pack() {
        super.pack();
        if (orientation == Orientation.HORIZONTAL) {
            var oneWeightWidth = ((getWidth().getValue() - elements.stream()
                    .filter(e -> !isElementHasWeight(e))
                    .mapToInt(e -> e.getWidth().getValue()).sum()) /
                    elements.stream().filter(LinearLayout::isElementHasWeight)
                            .mapToInt(LinearLayout::getElementWeight).sum());
            elements.stream().filter(LinearLayout::isElementHasWeight)
                    .forEach(e -> e.getWidth().setValue(oneWeightWidth * getElementWeight(e)));
            var offset = 0;
            for (val e : elements) {
                e.setX(offset);
                offset += e.getWidth().getValue();
            }
        } else {
            var oneWeightHeight = ((getHeight().getValue() - elements.stream()
                    .filter(e -> !isElementHasWeight(e))
                    .mapToInt(e -> e.getHeight().getValue()).sum()) /
                    elements.stream().filter(LinearLayout::isElementHasWeight)
                            .mapToInt(LinearLayout::getElementWeight).sum());
            elements.stream().filter(LinearLayout::isElementHasWeight)
                    .forEach(e -> e.getHeight().setValue(oneWeightHeight * getElementWeight(e)));
            var offset = 0;
            for (val e : elements) {
                e.setY(offset);
                offset += e.getHeight().getValue();
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
