package com.github.mjaroslav.ihategui.model.layout;

import com.github.mjaroslav.ihategui.model.Layout;
import com.github.mjaroslav.ihategui.model.element.Button;
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
            setClientSize(600, 400);
        }
    }

    @Test
    public void pack$vertical() throws Exception {
        val loaderActual = new ViewLoader();
        loaderActual.load(TestLinearLayout.class.getResourceAsStream("pack$vertical.json5"));
        val actualRoot = new RootLayout();
        val actualLayout = loaderActual.getContainer();
        actualRoot.addElement(actualLayout);
        actualRoot.pack();
        val actualBtn = actualLayout.findElementById("btn");
        val actualBtn1 = actualLayout.findElementById("btn1");
        val actualBtn2 = actualLayout.findElementById("btn2");

        val expectedBtn = new Button();
        expectedBtn.setClientSize(600, 200);
        val expectedBtn1 = new Button();
        expectedBtn1.setClientSize(300, 100);
        expectedBtn1.setY(200);
        expectedBtn1.setX(150);
        val expectedBtn2 = new Button();
        expectedBtn2.setClientSize(600, 100);
        expectedBtn2.setY(300);

        LayoutUtils.assertPackedSizeAndPosEquals(expectedBtn, actualBtn);
        LayoutUtils.assertPackedSizeAndPosEquals(expectedBtn1, actualBtn1);
        LayoutUtils.assertPackedSizeAndPosEquals(expectedBtn2, actualBtn2);
    }

    @Test
    public void pack$horizontal() throws Exception {
        val loaderActual = new ViewLoader();
        loaderActual.load(TestLinearLayout.class.getResourceAsStream("pack$horizontal.json5"));
        val actualRoot = new RootLayout();
        val actualLayout = loaderActual.getContainer();
        actualRoot.addElement(actualLayout);
        actualRoot.pack();
        val actualBtn = actualLayout.findElementById("btn");
        val actualBtn1 = actualLayout.findElementById("btn1");
        val actualBtn2 = actualLayout.findElementById("btn2");

        val expectedBtn = new Button();
        expectedBtn.setClientSize(200, 400);
        val expectedBtn1 = new Button();
        expectedBtn1.setClientSize(100, 200);
        expectedBtn1.setX(200);
        expectedBtn1.setY(100);
        val expectedBtn2 = new Button();
        expectedBtn2.setClientSize(300, 400);
        expectedBtn2.setX(300);

        LayoutUtils.assertPackedSizeAndPosEquals(expectedBtn, actualBtn);
        LayoutUtils.assertPackedSizeAndPosEquals(expectedBtn1, actualBtn1);
        LayoutUtils.assertPackedSizeAndPosEquals(expectedBtn2, actualBtn2);
    }
}
