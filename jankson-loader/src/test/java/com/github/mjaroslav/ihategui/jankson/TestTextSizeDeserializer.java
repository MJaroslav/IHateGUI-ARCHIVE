package com.github.mjaroslav.ihategui.jankson;

import blue.endless.jankson.Jankson;
import blue.endless.jankson.JsonPrimitive;
import com.github.mjaroslav.ihategui.api.model.TextSize;
import lombok.val;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestTextSizeDeserializer {
    private static final String KEY_TEST = "test";

    private Jankson jankson;

    @Before
    public void initialize() {
        jankson = Jankson.builder()
                .registerDeserializer(JsonPrimitive.class, TextSize.class, new TextSizeDeserializer()).build();
    }

    @Test
    public void apply$valueDefault() {
        val actual = JsonElementUtils
                .buildObjectForTests(jankson, TextSize.class, "DEFAULT");
        val expected = TextSize.of("DEFAULT");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void apply$valuePercent() {
        val actual = JsonElementUtils
                .buildObjectForTests(jankson, TextSize.class, "30%");
        val expected = TextSize.of("30%");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void apply$valueConstant() {
        val actual = JsonElementUtils
                .buildObjectForTests(jankson, TextSize.class, "14");
        val expected = TextSize.of("14");
        Assert.assertEquals(expected, actual);
    }
}
