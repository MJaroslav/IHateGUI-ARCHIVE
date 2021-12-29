package com.github.mjaroslav.ihategui.model.layout;

import blue.endless.jankson.JsonObject;
import com.github.mjaroslav.ihategui.model.Dimension;
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
                    .filter(e -> e.getWidth().getType() != Dimension.Type.WEIGHT)
                    .mapToInt(e -> e.getWidth().getValue()).sum()) /
                    elements.stream().filter(e -> e.getWidth().getType() == Dimension.Type.WEIGHT)
                            .mapToInt(e -> e.getWidth().getWeight()).sum());
            elements.stream().filter(e -> e.getWidth().getType() == Dimension.Type.WEIGHT)
                    .forEach(e -> e.getWidth().setValue(oneWeightWidth * e.getWidth().getWeight()));
            var offset = 0;
            for (val e : elements) {
                e.setX(offset);
                offset += e.getWidth().getValue();
            }
        } else {
            var oneWeightHeight = ((getHeight().getValue() - elements.stream()
                    .filter(e -> e.getHeight().getType() != Dimension.Type.WEIGHT)
                    .mapToInt(e -> e.getHeight().getValue()).sum()) /
                    elements.stream().filter(e -> e.getHeight().getType() == Dimension.Type.WEIGHT)
                            .mapToInt(e -> e.getHeight().getWeight()).sum());
            elements.stream().filter(e -> e.getHeight().getType() == Dimension.Type.WEIGHT)
                    .forEach(e -> e.getHeight().setValue(oneWeightHeight * e.getHeight().getWeight()));
            var offset = 0;
            for (val e : elements) {
                e.setY(offset);
                offset += e.getHeight().getValue();
            }
        }
    }
}
