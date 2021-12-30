package com.github.mjaroslav.ihategui.view;

import com.github.mjaroslav.ihategui.model.Orientation;
import com.github.mjaroslav.ihategui.model.element.Button;
import com.github.mjaroslav.ihategui.model.layout.LinearLayout;
import lombok.EqualsAndHashCode;
import lombok.val;
import org.junit.Assert;
import org.junit.Test;

public class TestViewLoader {
    @EqualsAndHashCode
    public static class EmptyController {
    }

    @Test
    public void load() {
        val loader = new ViewLoader();
        //loader.load(TestViewLoader.class.getResourceAsStream("load.json"));

        val actual = loader.container;

        val expected = new LinearLayout();
        expected.setOrientation(Orientation.VERTICAL);
        expected.setController(new EmptyController());

        val button = new Button();
        button.setId("btn1");
        button.setText("test");
        button.getMargin().setAll(10, 15, 5, 5);
        button.getPadding().setAll(1);
        button.setEnabled(false);

        expected.getElements().add(button);

        Assert.assertEquals(expected, actual);
    }
}
