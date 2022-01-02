package com.github.mjaroslav.ihategui.view.jankson;

import blue.endless.jankson.JsonPrimitive;
import blue.endless.jankson.api.DeserializerFunction;
import blue.endless.jankson.api.Marshaller;
import com.github.mjaroslav.ihategui.api.model.TextSize;

final class TextSizeDeserializer implements DeserializerFunction<JsonPrimitive, TextSize> {
    @Override
    public TextSize apply(JsonPrimitive object, Marshaller m) {
        return TextSize.of(object.asString());
    }
}
