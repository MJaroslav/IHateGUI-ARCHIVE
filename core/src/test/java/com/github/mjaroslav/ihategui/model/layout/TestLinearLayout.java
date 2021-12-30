package com.github.mjaroslav.ihategui.model.layout;

import com.github.mjaroslav.ihategui.model.Dimension;
import com.github.mjaroslav.ihategui.model.Layout;
import com.github.mjaroslav.ihategui.view.ViewLoader;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.val;
import org.junit.Test;

public class TestLinearLayout {
    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class RootLayout extends Layout {
        public RootLayout() {
            getWidth().setValue(600);
            getWidth().setType(Dimension.Type.VALUE);
            getHeight().setValue(400);
            getHeight().setType(Dimension.Type.VALUE);
        }
    }

    @Test
    public void pack$vertical() throws Exception {
        val loaderActual = new ViewLoader();
        loaderActual.load(TestLinearLayout.class.getResourceAsStream("pack$vertical_actual.json5"));
        val actualRoot = new RootLayout();
        val actualLayout = loaderActual.getContainer();
        actualRoot.addElement(actualLayout);
        actualRoot.pack();
        val actualBtn = actualLayout.findElementById("btn");
        val actualBtn1 = actualLayout.findElementById("btn1");
        val actualBtn2 = actualLayout.findElementById("btn2");

        val loaderExpected = new ViewLoader();
        loaderExpected.load(TestLinearLayout.class.getResourceAsStream("pack$vertical_expected.json5"));
        val expectedLayout = (LinearLayout) loaderExpected.getContainer();
        val expectedBtn = expectedLayout.findElementById("btn");
        val expectedBtn1 = expectedLayout.findElementById("btn1");
        val expectedBtn2 = expectedLayout.findElementById("btn2");

        LayoutUtils.assertPackedSizeAndPosEquals(expectedBtn, actualBtn);
        LayoutUtils.assertPackedSizeAndPosEquals(expectedBtn1, actualBtn1);
        LayoutUtils.assertPackedSizeAndPosEquals(expectedBtn2, actualBtn2);
    }

    @Test
    public void pack$horizontal() throws Exception {
        val loaderActual = new ViewLoader();
        loaderActual.load(TestLinearLayout.class.getResourceAsStream("pack$horizontal_actual.json5"));
        val actualRoot = new RootLayout();
        val actualLayout = loaderActual.getContainer();
        actualRoot.addElement(actualLayout);
        actualRoot.pack();
        val actualBtn = actualLayout.findElementById("btn");
        val actualBtn1 = actualLayout.findElementById("btn1");
        val actualBtn2 = actualLayout.findElementById("btn2");

        val loaderExpected = new ViewLoader();
        loaderExpected.load(TestLinearLayout.class.getResourceAsStream("pack$horizontal_expected.json5"));
        val expectedLayout = (LinearLayout) loaderExpected.getContainer();
        val expectedBtn = expectedLayout.findElementById("btn");
        val expectedBtn1 = expectedLayout.findElementById("btn1");
        val expectedBtn2 = expectedLayout.findElementById("btn2");

        LayoutUtils.assertPackedSizeAndPosEquals(expectedBtn, actualBtn);
        LayoutUtils.assertPackedSizeAndPosEquals(expectedBtn1, actualBtn1);
        LayoutUtils.assertPackedSizeAndPosEquals(expectedBtn2, actualBtn2);
    }
}
