package com.github.mjaroslav.ihategui.view.jankson;

import blue.endless.jankson.Jankson;
import blue.endless.jankson.JsonArray;
import blue.endless.jankson.JsonObject;
import blue.endless.jankson.JsonPrimitive;
import com.github.mjaroslav.ihategui.api.model.Container;
import com.github.mjaroslav.ihategui.api.model.Node;
import com.github.mjaroslav.ihategui.api.view.ViewLoader;
import com.github.mjaroslav.ihategui.model.TextSize;
import com.github.mjaroslav.ihategui.util.Pair.IntPair;
import com.github.mjaroslav.ihategui.util.ReflectionHelper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.InputStream;

@RequiredArgsConstructor
public final class JanksonViewLoader implements ViewLoader {
    @NotNull
    private final InputStream viewSource;

    private final NodeDeserializer deserializer = new NodeDeserializer();

    @Getter
    // Should be @NotNull, but constructor should be without this parameter
    private Container rootContainer;
    @Nullable
    @Getter
    private Object controller;
    private Jankson jankson;

    @Override
    public void loadViewModel() throws Exception {
        val jankson = getJankson();
        JsonObject object = jankson.load(viewSource);
        val controller = object.get(String.class, "controller");
        if (controller != null)
            this.controller = ReflectionHelper.createClassInstance(controller);
        val imports = (JsonArray) object.get("imports");
        if (imports != null)
            for (val element : imports)
                deserializer.nodeImport(((JsonPrimitive) element).asString());
        rootContainer = (Container) jankson.fromJsonCarefully(object, Node.class);
    }

    @NotNull
    private Jankson getJankson() {
        if (jankson == null)
            jankson = buildJankson();
        return jankson;
    }

    @NotNull
    private Jankson buildJankson() {
        return Jankson.builder()
                .registerDeserializer(JsonPrimitive.class, IntPair.class, new IntPairDeserializer())
                .registerDeserializer(JsonObject.class, Node.class, deserializer)
                .registerDeserializer(JsonPrimitive.class, TextSize.class, new TextSizeDeserializer())
                .build();
    }
}
