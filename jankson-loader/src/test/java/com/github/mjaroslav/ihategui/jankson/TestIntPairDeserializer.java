package com.github.mjaroslav.ihategui.jankson;

import blue.endless.jankson.Jankson;
import blue.endless.jankson.JsonPrimitive;
import com.github.mjaroslav.ihategui.util.Pair.IntPair;
import lombok.val;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestIntPairDeserializer {
    private static final String KEY_TEST = "test";

    private Jankson jankson;

    @Before
    public void initialize() {
        jankson = Jankson.builder()
                .registerDeserializer(JsonPrimitive.class, IntPair.class, new IntPairDeserializer()).build();
    }

    @Test
    public void deserialize$twoParams() {
        val actual = JsonElementUtils
                .buildObjectForTests(jankson, IntPair.class, "15 10");
        val expected = new IntPair(15, 10);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void deserialize$oneParam() {
        val actual = JsonElementUtils
                .buildObjectForTests(jankson, IntPair.class, "15");
        val expected = new IntPair(15, 15);
        Assert.assertEquals(expected, actual);
    }
}
