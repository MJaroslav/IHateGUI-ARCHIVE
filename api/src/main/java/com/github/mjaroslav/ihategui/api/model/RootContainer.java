package com.github.mjaroslav.ihategui.api.model;

import com.github.mjaroslav.ihategui.util.Pair.IntPair;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface RootContainer {
    @NotNull Container getContainer();

    void setContainer(@NonNull Container container);

    @Nullable Node findNodeById(@NotNull String id);

    void initContainer();

    void setRootSize(@NotNull IntPair rootSize);

    void setRootSize(int width, int height);

    @NotNull
    IntPair getRootSize();
}
