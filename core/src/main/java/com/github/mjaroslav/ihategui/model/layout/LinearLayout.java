package com.github.mjaroslav.ihategui.model.layout;

import com.github.mjaroslav.ihategui.api.model.Container;
import com.github.mjaroslav.ihategui.api.model.Node;
import com.github.mjaroslav.ihategui.api.model.RootContainer;
import com.github.mjaroslav.ihategui.api.model.adapter.ContainerAdapter;
import com.github.mjaroslav.ihategui.model.Orientation;
import com.github.mjaroslav.ihategui.util.Pair.IntPair;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter
public class LinearLayout extends ContainerAdapter {
    protected Orientation orientation = Orientation.HORIZONTAL;

    public static boolean isNodeHasWeight(Node node) {
        return getNodeWeight(node) > 0;
    }

    public static int getNodeWeight(Node node) {
        return (int) node.getAttribute("layout_weight", 0L).longValue();
    }

    public static void setNodeWeight(Node node, int value) {
        node.setAttribute("layout_weight", (long) value);
    }

    @Override
    public void initContainer() {
        // TODO: DO THIS
    }

    @NotNull
    @Builder
    private static LinearLayout $builder(Orientation orientation, IntPair size,
                                         IntPair position, boolean enable, boolean visible,
                                         String id, Container parent, RootContainer root,
                                         Map<String, Object> attributes) {
        val result = new LinearLayout();
        result.setOrientation(orientation);
        Node.setAllParams(result, size, position, enable, visible, id, parent, root, attributes);
        return result;
    }
}
