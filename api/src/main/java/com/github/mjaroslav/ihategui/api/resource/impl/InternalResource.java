package com.github.mjaroslav.ihategui.api.resource.impl;

import com.github.mjaroslav.ihategui.api.resource.Resource;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;

@ToString
@Getter
@RequiredArgsConstructor(staticName = "of")
public final class InternalResource implements Resource {
    @NotNull
    private final String path;

    @Override
    public @NotNull InputStream load() throws IOException {
        var formattedPath = path;
        if (!formattedPath.startsWith("/")) // Should be absolute
            formattedPath = "/" + formattedPath;
        val result = Thread.currentThread().getContextClassLoader().getResourceAsStream(formattedPath);
        if (result != null)
            return result;
        throw new IOException(String.format("Internal resource not found: %s", path));
    }
}
