package com.github.mjaroslav.ihategui.view;

import com.github.mjaroslav.ihategui.api.model.impl.RootAdapter;
import com.github.mjaroslav.ihategui.model.Orientation;
import com.github.mjaroslav.ihategui.model.element.Button;
import com.github.mjaroslav.ihategui.model.layout.LinearLayout;
import com.github.mjaroslav.ihategui.view.test.ClassForTest;
import com.github.mjaroslav.ihategui.view.test.ClassForTest1;
import com.github.mjaroslav.ihategui.view.test.treetest.ClassForTest2;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.val;
import org.junit.Assert;
import org.junit.Test;

public class TestViewLoader {
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    @Data
    public static class TestRoot extends RootAdapter {
        public TestRoot() {
            setRootWidth(600);
            setRootHeight(400);
        }
    }

    @EqualsAndHashCode
    public static class EmptyController {
    }

    @Test
    public void load() throws Exception {
        val actualRoot = new TestRoot();
        actualRoot.setRootLoader(new ViewLoader());
        actualRoot.setRootContainerStr("/com/github/mjaroslav/ihategui/view/load.json5");

        val expectedRoot = new TestRoot();
        expectedRoot.setController(new EmptyController());
        val expected = new LinearLayout();
        expected.setOrientation(Orientation.VERTICAL);
        expectedRoot.setRootContainer(expected);
        val expectedButton = new Button();
        expectedButton.setId("btn1");
        expectedButton.setText("test");
        expectedButton.setMarginStr("10;15;5;5");
        expectedButton.setPaddingStr("1");
        expectedButton.setEnabled(false);
        expected.addNode(expectedButton);

        Assert.assertNotNull("Controller not created!", actualRoot.getController());
        Assert.assertEquals("Controllers not equals!", EmptyController.class, actualRoot.getController().getClass());
        Assert.assertEquals(expectedRoot, actualRoot);
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
