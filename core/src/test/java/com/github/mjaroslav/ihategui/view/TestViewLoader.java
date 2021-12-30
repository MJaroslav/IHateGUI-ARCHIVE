package com.github.mjaroslav.ihategui.view;

import com.github.mjaroslav.ihategui.model.Orientation;
import com.github.mjaroslav.ihategui.model.element.Button;
import com.github.mjaroslav.ihategui.model.layout.LinearLayout;
import com.github.mjaroslav.ihategui.view.test.ClassForTest;
import com.github.mjaroslav.ihategui.view.test.ClassForTest1;
import com.github.mjaroslav.ihategui.view.test.treetest.ClassForTest2;
import lombok.EqualsAndHashCode;
import lombok.val;
import org.junit.Assert;
import org.junit.Test;

public class TestViewLoader {
    @EqualsAndHashCode
    public static class EmptyController {
    }

    @Test
    public void load() throws Exception {
        val loader = new ViewLoader();
        loader.load(TestViewLoader.class.getResourceAsStream("load.json5"));

        val actual = loader.container;

        val expected = new LinearLayout();
        expected.setLoader(loader);
        expected.setOrientation(Orientation.VERTICAL);

        val button = new Button();
        button.setId("btn1");
        button.setText("test");
        button.getMargin().set(10, 15, 5, 5);
        button.getPadding().set(1);
        button.setEnabled(false);

        expected.getElements().add(button);

        Assert.assertEquals("Controllers not equals!", EmptyController.class, loader.getController().getClass());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void importToDeserializer$single() throws Exception {
        val loader = new ViewLoader();
        loader.importToDeserializer("com.github.mjaroslav.ihategui.view.test.ClassForTest");
        Assert.assertEquals("Can't find expected class!", ClassForTest.class, loader.getImported().get("ClassForTest"));
        Assert.assertEquals("Another imports found or required not imported!", 1, loader.imported.size());
    }

    @Test
    public void importToDeserializer$many() throws Exception {
        val loader = new ViewLoader();
        loader.importToDeserializer("com.github.mjaroslav.ihategui.view.test.ClassForTest");
        loader.importToDeserializer("com.github.mjaroslav.ihategui.view.test.ClassForTest1");
        loader.importToDeserializer("com.github.mjaroslav.ihategui.view.test.treetest.ClassForTest2");
        Assert.assertEquals("Can't find expected class!", ClassForTest.class, loader.getImported().get("ClassForTest"));
        Assert.assertEquals("Can't find expected class!", ClassForTest1.class, loader.getImported().get("ClassForTest1"));
        Assert.assertEquals("Can't find expected class!", ClassForTest2.class, loader.getImported().get("ClassForTest2"));
        Assert.assertEquals("Another imports found or required not imported!", 3, loader.imported.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void importToDeserializer$twice() throws Exception {
        val loader = new ViewLoader();
        loader.importToDeserializer("com.github.mjaroslav.ihategui.view.test.ClassForTest");
        loader.importToDeserializer("com.github.mjaroslav.ihategui.view.test.ClassForTest");
    }

    @Test
    public void importToDeserializer$pronoun() throws Exception {
        val loader = new ViewLoader();
        loader.importToDeserializer("com.github.mjaroslav.ihategui.view.test.ClassForTest");
        loader.importToDeserializer("com.github.mjaroslav.ihategui.view.test.treetest.ClassForTest ClassForTest1");
        Assert.assertEquals("Can't find expected class!", ClassForTest.class, loader.getImported().get("ClassForTest"));
        Assert.assertEquals("Can't find expected class!", com.github.mjaroslav.ihategui.view.test.treetest.ClassForTest.class, loader.getImported().get("ClassForTest1"));
        Assert.assertEquals("Another imports found or required not imported!", 2, loader.imported.size());
    }

    @Test
    public void importToDeserializer$package() throws Exception {
        val loader = new ViewLoader();
        loader.importToDeserializer("com.github.mjaroslav.ihategui.view.test.*");
        Assert.assertEquals("Can't find expected class!", ClassForTest.class, loader.getImported().get("ClassForTest"));
        Assert.assertEquals("Can't find expected class!", ClassForTest1.class, loader.getImported().get("ClassForTest1"));
        Assert.assertEquals("Another imports found or required not imported!", 2, loader.imported.size());
    }
}
