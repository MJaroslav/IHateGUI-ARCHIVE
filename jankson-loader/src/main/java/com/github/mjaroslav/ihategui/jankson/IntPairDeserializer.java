package com.github.mjaroslav.ihategui.jankson;

import blue.endless.jankson.JsonPrimitive;
import blue.endless.jankson.api.DeserializationException;
import blue.endless.jankson.api.DeserializerFunction;
import blue.endless.jankson.api.Marshaller;
import com.github.mjaroslav.ihategui.util.Pair.IntPair;
import lombok.val;

final class IntPairDeserializer implements DeserializerFunction<JsonPrimitive, IntPair> {
    @Override
    public IntPair apply(JsonPrimitive object, Marshaller m) throws DeserializationException {
        val value = object.asString().split(" ");
        val parsedValue = new int[value.length];
        try {
            for (int i = 0; i < value.length; i++)
                parsedValue[i] = Integer.parseInt(value[i]);
        } catch (NumberFormatException e) {
            throw new DeserializationException("Can't deserialize value", e);
        }
        if (parsedValue.length == 1)
            return new IntPair(parsedValue[0], parsedValue[0]);
        else if (parsedValue.length == 2)
            return new IntPair(parsedValue[0], parsedValue[1]);
        else throw new DeserializationException("Supported String format: \"int\" or \"int int\"");
    }
}
