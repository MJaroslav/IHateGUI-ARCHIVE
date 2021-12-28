package com.github.mjaroslav.ihategui.model;

import com.github.mjaroslav.ihategui.model.Element;
import lombok.Getter;

import java.util.List;

public abstract class Layout extends Element {
    @Getter
    protected List<Element> elements;
}
