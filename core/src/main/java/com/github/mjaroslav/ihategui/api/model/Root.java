package com.github.mjaroslav.ihategui.api.model;

import com.github.mjaroslav.ihategui.view.ViewLoader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Root {
    int getRootWidth();

    int getRootHeight();

    @Nullable
    Container getRootContainer();

    void setRootContainerStr(@NotNull String resourcePath) throws Exception;

    void setRootContainer(@NotNull Container container);

    @Nullable ViewLoader getRootLoader();

    @Nullable Node findNodeById(@NotNull String id);

    void pack();
}
