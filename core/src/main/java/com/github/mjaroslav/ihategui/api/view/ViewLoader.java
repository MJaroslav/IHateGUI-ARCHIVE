package com.github.mjaroslav.ihategui.api.view;

import com.github.mjaroslav.ihategui.api.model.Container;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ViewLoader {
    @Nullable
    Object getController();

    @NotNull Container getRootContainer();

    void loadViewModel() throws Exception;
}
