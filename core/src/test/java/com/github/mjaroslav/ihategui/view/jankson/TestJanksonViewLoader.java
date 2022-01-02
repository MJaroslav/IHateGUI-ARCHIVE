package com.github.mjaroslav.ihategui.view.jankson;

import com.github.mjaroslav.ihategui.api.model.Node;
import com.github.mjaroslav.ihategui.api.model.Orientation;
import com.github.mjaroslav.ihategui.api.model.RootContainer;
import com.github.mjaroslav.ihategui.api.model.impl.container.LinearLayout;
import com.github.mjaroslav.ihategui.api.model.impl.node.Button;
import com.github.mjaroslav.ihategui.controller.ControllerForTests;
import com.github.mjaroslav.ihategui.model.RootForTests;
import lombok.val;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Objects;

public class TestJanksonViewLoader {
    RootContainer expectedRoot;
    Object expectedController;

    @Before
    public void initialize() {
        expectedRoot = new RootForTests();
        val expectedButton = Button.builder().id("btn1").text("test").enable(false).size(200, 50).attributes(
                Node.attribBuilder().setAttribute("attribute_test", 1L).build()).build();
        val expectedContainer = LinearLayout.builder().orientation(Orientation.VERTICAL).visible(false).build();
        expectedContainer.addNode(expectedButton);
        expectedRoot.setContainer(expectedContainer);
        expectedController = new ControllerForTests();
    }

    @Test
    public void loadViewModel() throws Exception {
        val actualRoot = new RootForTests();
        val actualViewSource = Objects.requireNonNull(
                TestJanksonViewLoader.class.getResourceAsStream("loadViewModel.json5"));
        val loader = new JanksonViewLoader(actualViewSource, false, false);

        loader.loadViewModel();
        actualRoot.setContainer(loader.getRootContainer());
        val actualController = loader.getController();

        Assert.assertEquals("Controllers not equals!", expectedController, actualController);
        Assert.assertEquals(expectedRoot, actualRoot);
    }

    @Test
    public void loadViewModel$providedNodesImported() throws Exception {
        val actualRoot = new RootForTests();
        val actualViewSource = Objects.requireNonNull(
                TestJanksonViewLoader.class.getResourceAsStream("loadViewModel$providedNodesImported.json5"));
        val loader = new JanksonViewLoader(actualViewSource, true, true);

        loader.loadViewModel();
        actualRoot.setContainer(loader.getRootContainer());
        val actualController = loader.getController();

        Assert.assertEquals("Controllers not equals!", expectedController, actualController);
        Assert.assertEquals(expectedRoot, actualRoot);
    }

    @Test
    public void loadViewModel$noImports() throws Exception {
        val actualRoot = new RootForTests();
        val actualViewSource = Objects.requireNonNull(
                TestJanksonViewLoader.class.getResourceAsStream("loadViewModel$noImports.json5"));
        val loader = new JanksonViewLoader(actualViewSource, false, false);

        loader.loadViewModel();
        actualRoot.setContainer(loader.getRootContainer());
        val actualController = loader.getController();

        Assert.assertEquals("Controllers not equals!", expectedController, actualController);
        Assert.assertEquals(expectedRoot, actualRoot);
    }
}
