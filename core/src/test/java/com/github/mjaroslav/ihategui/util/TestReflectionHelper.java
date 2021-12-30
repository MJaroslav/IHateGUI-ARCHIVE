package com.github.mjaroslav.ihategui.util;

import com.github.mjaroslav.ihategui.util.test.ClassForTest;
import com.github.mjaroslav.ihategui.util.test.ClassForTest1;
import com.github.mjaroslav.ihategui.util.test.treetest.ClassForTest3;
import lombok.val;
import org.junit.Assert;
import org.junit.Test;

public class TestReflectionHelper {
    @Test
    public void getClassesInPackage() throws Exception {
        val classes = ReflectionHelper.getClassesInPackage("com.github.mjaroslav.ihategui.util.test", false);
        Assert.assertTrue("Expected class not found!", classes.contains(ClassForTest.class));
        Assert.assertTrue("Expected class not found!", classes.contains(ClassForTest1.class));
        Assert.assertFalse("Expected class must not be found!", classes.contains(ClassForTest3.class));
        Assert.assertEquals("Another classes was found or required classes not!", 2, classes.size());
    }

    @Test
    public void getClassesInPackageTree() throws Exception {
        val classes = ReflectionHelper.getClassesInPackage("com.github.mjaroslav.ihategui.util.test", true);
        Assert.assertTrue("Expected class not found!", classes.contains(ClassForTest.class));
        Assert.assertTrue("Expected class not found!", classes.contains(ClassForTest1.class));
        Assert.assertTrue("Expected class not found!", classes.contains(ClassForTest3.class));
        Assert.assertEquals("Another classes was found or required classes not!", 3, classes.size());
    }
}
