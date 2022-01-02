package com.github.mjaroslav.ihategui.model;

import com.github.mjaroslav.ihategui.api.model.adapter.RootAdapter;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RootForTests extends RootAdapter {
    public static final int WIDTH = 600;
    public static final int HEIGHT = 400;

    public RootForTests() {
        setRootSize(WIDTH, HEIGHT);
    }

    @Override
    public void initContainer() {

    }
}
