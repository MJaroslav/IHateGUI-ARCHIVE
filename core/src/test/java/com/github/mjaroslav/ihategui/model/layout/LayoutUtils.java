package com.github.mjaroslav.ihategui.model.layout;

import com.github.mjaroslav.ihategui.api.model.Node;
import org.junit.Assert;

public class LayoutUtils {
    public static void assertPackedSizeAndPosEquals(Node expected, Node actual) {
        Assert.assertEquals("Width didn't match!", expected.getTotalWidth(), actual.getTotalWidth());
        Assert.assertEquals("Height didn't match!", expected.getTotalHeight(), actual.getTotalHeight());
        Assert.assertEquals("X didn't match!", expected.getX(), actual.getX());
        Assert.assertEquals("Y didn't match!", expected.getY(), actual.getY());
    }
}
