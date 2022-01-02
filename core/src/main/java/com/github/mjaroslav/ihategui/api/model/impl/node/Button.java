package com.github.mjaroslav.ihategui.api.model.impl.node;

import com.github.mjaroslav.ihategui.api.model.*;
import com.github.mjaroslav.ihategui.api.model.adapter.NodeAdapter;
import com.github.mjaroslav.ihategui.util.Pair.IntPair;
import lombok.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class Button extends NodeAdapter {
    @Nullable
    protected String text = "";
    @NotNull
    protected TextSize textSize = TextSize.of("default");
    @NotNull
    protected Alignment textAlignment = Alignment.CENTER;

    @NotNull
    @Builder
    private static Button $builder(String text, TextSize textSize, Alignment textAlignment,
                                   IntPair size, IntPair position, boolean enable, boolean visible,
                                   String id, Container parent, RootContainer root,
                                   Map<String, Object> attributes) {
        val result = new Button();
        result.setText(text);
        if (textSize != null)
            result.setTextSize(textSize);
        if (textAlignment != null)
            result.setTextAlignment(textAlignment);
        Node.setAllParams(result, size, position, enable, visible, id, parent, root, attributes);
        return result;
    }

    // TODO: Do something with this
    public static class ButtonBuilder {
        public ButtonBuilder size(int width, int height) {
            size = new IntPair(width, height);
            return this;
        }

        public ButtonBuilder position(int x, int y) {
            position = new IntPair(x, y);
            return this;
        }
    }
}
