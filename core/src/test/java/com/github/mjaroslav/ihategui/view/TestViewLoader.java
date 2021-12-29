package com.github.mjaroslav.ihategui.view;

import com.github.mjaroslav.ihategui.model.Layout;
import com.github.mjaroslav.ihategui.model.Orientation;
import com.github.mjaroslav.ihategui.model.element.Button;
import com.github.mjaroslav.ihategui.model.layout.LinearLayout;
import lombok.EqualsAndHashCode;
import org.junit.Assert;
import org.junit.Test;

public class TestViewLoader {
    @EqualsAndHashCode
    public static class EmptyController {
    }

    @Test
    public void testLoad() {
        ViewLoader loader = new ViewLoader();
        loader.load(TestViewLoader.class.getResourceAsStream("test_view.json"));

        Layout actual = loader.container;

        LinearLayout expected = new LinearLayout();
        expected.setOrientation(Orientation.VERTICAL);
        expected.setController(new EmptyController());

        Button button = new Button();
        button.setId("btn1");
        button.setText("test");
        button.getMargin().setAll(10, 15, 5, 5);
        button.getPadding().setAll(1);
        button.setEnabled(false);

        expected.getElements().add(button);

        Assert.assertEquals(expected, actual);
    }
}
