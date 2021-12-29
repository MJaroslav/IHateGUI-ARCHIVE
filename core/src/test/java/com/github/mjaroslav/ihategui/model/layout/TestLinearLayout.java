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

        val actual = new LinearLayout();
        actual.setOrientation(Orientation.VERTICAL);
        val actualBtn = new Button();
        actualBtn.setHeight("2w");
        val actualBtn1 = new Button();
        actualBtn1.setHeight("1w");
        val actualBtn2 = new Button();
        actualBtn2.setHeight("1w");
        actual.addElements(actualBtn, actualBtn1, actualBtn2);
        actualRoot.addElement(actual);

        actualRoot.pack();

        val expectedRoot = new RootLayout();

        val expected = new LinearLayout();
        expected.getWidth().setValue(600);
        expected.getHeight().setValue(400);
        expected.setOrientation(Orientation.VERTICAL);
        val expectedBtn = new Button();
        expectedBtn.setHeight("2w");
        expectedBtn.getWidth().setValue(600);
        expectedBtn.getHeight().setValue(200);
        val expectedBtn1 = new Button();
        expectedBtn1.setHeight("1w");
        expectedBtn1.getWidth().setValue(600);
        expectedBtn1.getHeight().setValue(100);
        expectedBtn1.setY(200);
        val expectedBtn2 = new Button();
        expectedBtn2.setHeight("1w");
        expectedBtn2.getWidth().setValue(600);
        expectedBtn2.getHeight().setValue(100);
        expectedBtn2.setY(300);
        expected.addElements(expectedBtn, expectedBtn1, expectedBtn2);
        expectedRoot.addElement(expected);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void packHorizontal() {
        val actualRoot = new RootLayout();

        val actual = new LinearLayout();
        val actualBtn = new Button();
        actualBtn.setWidth("2w");
        val actualBtn1 = new Button();
        actualBtn1.setWidth("1w");
        val actualBtn2 = new Button();
        actualBtn2.setWidth("3w");
        actual.addElements(actualBtn, actualBtn1, actualBtn2);
        actualRoot.addElement(actual);

        actualRoot.pack();

        val expectedRoot = new RootLayout();

        val expected = new LinearLayout();
        expected.getWidth().setValue(600);
        expected.getHeight().setValue(400);
        val expectedBtn = new Button();
        expectedBtn.setWidth("2w");
        expectedBtn.getWidth().setValue(200);
        expectedBtn.getHeight().setValue(400);
        val expectedBtn1 = new Button();
        expectedBtn1.setWidth("1w");
        expectedBtn1.getWidth().setValue(100);
        expectedBtn1.getHeight().setValue(400);
        expectedBtn1.setX(200);
        val expectedBtn2 = new Button();
        expectedBtn2.setWidth("3w");
        expectedBtn2.getWidth().setValue(300);
        expectedBtn2.getHeight().setValue(400);
        expectedBtn2.setX(300);
        expected.addElements(expectedBtn, expectedBtn1, expectedBtn2);
        expectedRoot.addElement(expected);

        Assert.assertEquals(expected, actual);
    }
}
