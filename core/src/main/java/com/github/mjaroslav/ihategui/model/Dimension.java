package com.github.mjaroslav.ihategui.model;

import lombok.Data;

@Data
public class Dimension {
    protected Type type = Type.MATCH_PARENT;
    protected int value = 0;
    protected int weight = 1;

    public void loadFromValue(String value) {
        if (value == null)
            return;
        type = Type.fromValue(value);
        this.value = type == Type.VALUE ? Integer.parseInt(value) : this.value;
        weight = type == Type.WEIGHT ? Integer.parseInt(value.substring(0, value.length() - 1)) : weight;
    }

    public enum Type {
        MATCH_PARENT, MATCH_CONTENT, VALUE, WEIGHT;

        public static Type fromValue(String value) {
            try {
                return valueOf(value.toUpperCase());
            } catch (IllegalArgumentException ignored) {
                try {
                    if (value.toUpperCase().endsWith("W")) {
                        String percent = value.substring(0, value.length() - 1);
                        Float.parseFloat(percent);
                        return WEIGHT;
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
