package com.github.mjaroslav.ihategui.model;

import lombok.Data;

@Data
public class Dimension {
    protected Type type = Type.MATCH_PARENT;
    protected int value = 0;
    protected float percent;

    public void loadFromValue(String value) {
        if (value == null)
            return;
        type = Type.fromValue(value);
        this.value = type == Type.VALUE ? Integer.parseInt(value) : this.value;
        percent = type == Type.PERCENT ? Float.parseFloat(value.substring(0, value.length() - 1)) : percent;
    }

    public enum Type {
        MATCH_PARENT, MATCH_CONTENT, VALUE, PERCENT;

        public static Type fromValue(String value) {
            try {
                return valueOf(value.toUpperCase());
            } catch (IllegalArgumentException ignored) {
                try {
                    if (value.endsWith("%")) {
                        Float.parseFloat(value.substring(0, value.length() - 1));
                        return PERCENT;
                    } else {
                        Integer.parseInt(value);
                        return VALUE;
                    }
                } catch (NumberFormatException ignored1) {
                    return MATCH_PARENT;
                }
            }
        }
    }
}
