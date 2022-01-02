package com.github.mjaroslav.ihategui.model.layout;

import com.github.mjaroslav.ihategui.api.model.Node;
import org.junit.Assert;

public class LayoutUtils {
    public static void assertSizeAndPosEquals(Node expected, Node actual) {
        Assert.assertEquals("Size didn't match!", expected.getSize(), actual.getSize());
        Assert.assertEquals("Height didn't match!", expected.getPosition(), actual.getPosition());
    }
}
