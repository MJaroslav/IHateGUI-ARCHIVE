package com.github.mjaroslav.ihategui.model.layout;

import com.github.mjaroslav.ihategui.model.Dimension;
import com.github.mjaroslav.ihategui.model.Layout;
import com.github.mjaroslav.ihategui.model.Orientation;
import com.github.mjaroslav.ihategui.model.element.Button;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.val;
import org.junit.Assert;
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
    public void packVertical() {
        val actualRoot = new RootLayout();
        val actualLayout = new LinearLayout();
        actualLayout.setOrientation(Orientation.VERTICAL);
        val actualBtn = new Button();
        actualBtn.setHeight("0");
        LinearLayout.setElementWeight(actualBtn, 2);
        val actualBtn1 = new Button();
        actualBtn1.setHeight("0");
        LinearLayout.setElementWeight(actualBtn1, 1);
        val actualBtn2 = new Button();
        actualBtn2.setHeight("0");
        LinearLayout.setElementWeight(actualBtn2, 1);
        actualLayout.addElements(actualBtn, actualBtn1, actualBtn2);
        actualRoot.addElement(actualLayout);
        actualRoot.pack();

        val expectedBtn = new Button();
        expectedBtn.setWidth("600");
        expectedBtn.setHeight("200");
        expectedBtn.setY(0);
        val expectedBtn1 = new Button();
        expectedBtn1.setWidth("600");
        expectedBtn1.setHeight("100");
        expectedBtn1.setY(200);
        val expectedBtn2 = new Button();
        expectedBtn2.setWidth("600");
        expectedBtn2.setHeight("100");
        expectedBtn2.setY(300);

        LayoutUtils.assertPackedSizeAndPosEquals(expectedBtn, actualBtn);
        LayoutUtils.assertPackedSizeAndPosEquals(expectedBtn1, actualBtn1);
        LayoutUtils.assertPackedSizeAndPosEquals(expectedBtn2, actualBtn2);
    }

    @Test
    public void packHorizontal() {
        val actualRoot = new RootLayout();
        val actualLayout = new LinearLayout();
        val actualBtn = new Button();
        actualBtn.setWidth("0");
        LinearLayout.setElementWeight(actualBtn, 2);
        val actualBtn1 = new Button();
        actualBtn1.setWidth("0");
        LinearLayout.setElementWeight(actualBtn1, 1);
        val actualBtn2 = new Button();
        actualBtn2.setWidth("0");
        LinearLayout.setElementWeight(actualBtn2, 3);
        actualLayout.addElements(actualBtn, actualBtn1, actualBtn2);
        actualRoot.addElement(actualLayout);
        actualRoot.pack();

        val expectedBtn = new Button();
        expectedBtn.setWidth("200");
        expectedBtn.setHeight("400");
        expectedBtn.setX(0);
        val expectedBtn1 = new Button();
        expectedBtn1.setWidth("100");
        expectedBtn1.setHeight("400");
        expectedBtn1.setX(200);
        val expectedBtn2 = new Button();
        expectedBtn2.setWidth("300");
        expectedBtn2.setHeight("400");
        expectedBtn2.setX(300);

        LayoutUtils.assertPackedSizeAndPosEquals(expectedBtn, actualBtn);
        LayoutUtils.assertPackedSizeAndPosEquals(expectedBtn1, actualBtn1);
        LayoutUtils.assertPackedSizeAndPosEquals(expectedBtn2, actualBtn2);
    }
}
