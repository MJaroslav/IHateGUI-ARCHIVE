package com.github.mjaroslav.ihategui.model.layout;

import blue.endless.jankson.JsonObject;
import blue.endless.jankson.JsonPrimitive;
import blue.endless.jankson.annotation.Deserializer;
import com.github.mjaroslav.ihategui.api.model.Node;
import com.github.mjaroslav.ihategui.api.model.impl.Layout;
import com.github.mjaroslav.ihategui.model.Alignment;
import com.github.mjaroslav.ihategui.model.Orientation;
import com.github.mjaroslav.ihategui.util.JsonHelper;
import lombok.*;
import org.jetbrains.annotations.NotNull;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class LinearLayout extends Layout {
    protected Orientation orientation = Orientation.HORIZONTAL;

    @Override
    public void loadFromJson(@NotNull JsonObject object) throws Exception {
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
        nodes.forEach(Node::calculateContentSize);
        nodes.forEach(Node::calculatePreferredSize);
        if (orientation == Orientation.HORIZONTAL) {
            var oneWeightWidth = (this.getTotalWidth() - nodes.stream()
                    .filter(node -> !isNodeHasWeight(node))
                    .mapToInt(Node::getTotalWidth).sum()) /
                    nodes.stream().filter(LinearLayout::isNodeHasWeight)
                            .mapToInt(LinearLayout::getNodeWeight).sum();
            nodes.stream().filter(LinearLayout::isNodeHasWeight)
                    .forEach(node -> node.setTotalWidth(oneWeightWidth * getNodeWeight(node)));
            var offset = 0;
            for (val node : nodes) {
                node.setX(offset);
                offset += node.getTotalWidth();
                if (alignment == Alignment.TOP)
                    node.setY(0);
                else if (alignment == Alignment.BOTTOM)
                    node.setY(this.getTotalHeight() - node.getTotalHeight());
                else if (alignment == Alignment.CENTER)
                    node.setY((this.getTotalHeight() - node.getTotalHeight()) / 2);
            }
        } else {
            var oneWeightHeight = (this.getTotalHeight() - nodes.stream()
                    .filter(node -> !isNodeHasWeight(node))
                    .mapToInt(Node::getTotalHeight).sum()) /
                    nodes.stream().filter(LinearLayout::isNodeHasWeight)
                            .mapToInt(LinearLayout::getNodeWeight).sum();
            nodes.stream().filter(LinearLayout::isNodeHasWeight)
                    .forEach(node -> node.setTotalHeight(oneWeightHeight * getNodeWeight(node)));
            var offset = 0;
            for (val node : nodes) {
                node.setY(offset);
                offset += node.getTotalHeight();
                if (alignment == Alignment.TOP)
                    node.setX(0);
                else if (alignment == Alignment.BOTTOM)
                    node.setX(this.getTotalWidth() - node.getTotalWidth());
                else if (alignment == Alignment.CENTER)
                    node.setX((this.getTotalWidth() - node.getTotalWidth()) / 2);
            }
        }
    }


    @Override
    public void calculateContentSize() {
        contentWidth = nodes.stream().mapToInt(Node::getTotalWidth).sum();
        contentHeight = nodes.stream().mapToInt(Node::getTotalHeight).sum();
    }

    public static boolean isNodeHasWeight(Node node) {
        return getNodeWeight(node) > 0;
    }

    public static int getNodeWeight(Node node) {
        return node.getExtraAttribute("linearlayout_weight", Integer.class, 0);
    }

    public static void setNodeWeight(Node node, int value) {
        node.setExtraAttribute("linearlayout_weight", new JsonPrimitive(value));
    }
}
