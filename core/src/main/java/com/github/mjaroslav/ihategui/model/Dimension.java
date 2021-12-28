package com.github.mjaroslav.ihategui.model;

import lombok.Data;

@Data
public class Dimension {
    protected Type type;
    protected int value;

    public enum Type {
        MATCH_PARENT, MATCH_CONTENT, VALUE;
    }
}
