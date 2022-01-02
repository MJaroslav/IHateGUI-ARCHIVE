package com.github.mjaroslav.ihategui.api.resource;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;

public interface Resource {
    @NotNull
    InputStream load() throws IOException;
}
