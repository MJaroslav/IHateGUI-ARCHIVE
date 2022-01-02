package com.github.mjaroslav.ihategui.view.jankson;

import com.github.mjaroslav.ihategui.api.model.Node;
import com.github.mjaroslav.ihategui.controller.ControllerForTests;
import com.github.mjaroslav.ihategui.model.Orientation;
import com.github.mjaroslav.ihategui.model.RootForTests;
import com.github.mjaroslav.ihategui.model.element.Button;
import com.github.mjaroslav.ihategui.model.layout.LinearLayout;
import lombok.val;
import org.junit.Assert;
import org.junit.Test;

import java.util.Objects;

public class TestJanksonViewLoader {
    @Test
    public void load() throws Exception {
        val actualRoot = new RootForTests();

        val viewSource = TestJanksonViewLoader.class.getResourceAsStream("/com/github/mjaroslav/ihategui/view/load.json5");
        val loader = new JanksonViewLoader(Objects.requireNonNull(viewSource));

        loader.loadViewModel();

        actualRoot.setContainer(loader.getRootContainer());

        val actualController = loader.getController();

        val expectedRoot = new RootForTests();
        val expectedButton = Button.builder().id("btn1").text("test").enable(false).attributes(
                Node.attribBuilder().setAttribute("attribute_test", 1L).build()).build();
        val expectedContainer = LinearLayout.builder().orientation(Orientation.VERTICAL).visible(false).build();
        expectedContainer.addNode(expectedButton);
        expectedRoot.setContainer(expectedContainer);

        val expectedController = new ControllerForTests();

        Assert.assertEquals("Controllers not equals!", expectedController, actualController);
        Assert.assertEquals(expectedRoot, actualRoot);
    }
}
