package com.github.mjaroslav.ihategui.model.layout;

import com.github.mjaroslav.ihategui.model.Element;
import org.junit.Assert;

public class LayoutUtils {
    public static void assertPackedSizeAndPosEquals(Element expected, Element actual) {
        Assert.assertEquals("Width didn't match!", expected.getClientWidth(), actual.getClientWidth());
        Assert.assertEquals("Height didn't match!", expected.getClientHeight(), actual.getClientHeight());
        Assert.assertEquals("X didn't match!", expected.getX(), actual.getX());
        Assert.assertEquals("Y didn't match!", expected.getY(), actual.getY());
    }
}
