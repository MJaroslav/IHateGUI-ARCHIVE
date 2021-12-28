package com.github.mjaroslav.ihategui.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Element {
    protected Dimension width;
    protected Dimension height;
    protected Padding padding;
    protected String id;
    protected boolean enabled;
    protected boolean visible;
}
