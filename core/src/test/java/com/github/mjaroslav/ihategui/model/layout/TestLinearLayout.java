package com.github.mjaroslav.ihategui.model.layout;

import com.github.mjaroslav.ihategui.api.model.impl.RootAdapter;
import com.github.mjaroslav.ihategui.model.element.Button;
import com.github.mjaroslav.ihategui.view.ViewLoader;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.val;
import org.junit.Assert;
import org.junit.Test;

public class TestLinearLayout {
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    @Data
    public static class TestRoot extends RootAdapter {
        public TestRoot() {
            setRootWidth(600);
            setRootHeight(400);
        }
    }

    @Test
    public void pack$vertical() throws Exception {
        val rootActual = new TestRoot();
        rootActual.setRootLoader(new ViewLoader());
        rootActual.setRootContainerStr("/com/github/mjaroslav/ihategui/model/layout/pack$vertical.json5");
        rootActual.pack();

        val actualBtn = rootActual.findNodeById("btn");
        Assert.assertNotNull("Node not found!", actualBtn);
        val actualBtn1 = rootActual.findNodeById("btn1");
        Assert.assertNotNull("Node not found!", actualBtn1);
        val actualBtn2 = rootActual.findNodeById("btn2");
        Assert.assertNotNull("Node not found!", actualBtn2);

        val expectedBtn = new Button();
        expectedBtn.setTotalSize(600, 200);
        val expectedBtn1 = new Button();
        expectedBtn1.setTotalSize(300, 100);
        expectedBtn1.setY(200);
        expectedBtn1.setX(150);
        val expectedBtn2 = new Button();
        expectedBtn2.setTotalSize(600, 100);
        expectedBtn2.setY(300);

        LayoutUtils.assertPackedSizeAndPosEquals(expectedBtn, actualBtn);
        LayoutUtils.assertPackedSizeAndPosEquals(expectedBtn1, actualBtn1);
        LayoutUtils.assertPackedSizeAndPosEquals(expectedBtn2, actualBtn2);
    }

    @Test
    public void pack$horizontal() throws Exception {
        val rootActual = new TestRoot();
        rootActual.setRootLoader(new ViewLoader());
        rootActual.setRootContainerStr("/com/github/mjaroslav/ihategui/model/layout/pack$horizontal.json5");
        rootActual.pack();

        val actualBtn = rootActual.findNodeById("btn");
        Assert.assertNotNull("Node not found!", actualBtn);
        val actualBtn1 = rootActual.findNodeById("btn1");
        Assert.assertNotNull("Node not found!", actualBtn1);
        val actualBtn2 = rootActual.findNodeById("btn2");
        Assert.assertNotNull("Node not found!", actualBtn2);

        val expectedBtn = new Button();
        expectedBtn.setTotalSize(200, 400);
        val expectedBtn1 = new Button();
        expectedBtn1.setTotalSize(100, 200);
        expectedBtn1.setX(200);
        expectedBtn1.setY(100);
        val expectedBtn2 = new Button();
        expectedBtn2.setTotalSize(300, 400);
        expectedBtn2.setX(300);

        LayoutUtils.assertPackedSizeAndPosEquals(expectedBtn, actualBtn);
        LayoutUtils.assertPackedSizeAndPosEquals(expectedBtn1, actualBtn1);
        LayoutUtils.assertPackedSizeAndPosEquals(expectedBtn2, actualBtn2);
    }
}
