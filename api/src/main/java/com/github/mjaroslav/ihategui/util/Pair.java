package com.github.mjaroslav.ihategui.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.Nullable;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Pair<A, B> {
    @Nullable
    protected A a;
    @Nullable
    protected B b;

    public void set(A a, B b) {
        this.a = a;
        this.b = b;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class IntPair extends Pair<Integer, Integer> {
        protected Integer a = 0;
        protected Integer b = 0;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DoublePair extends Pair<Double, Double> {
        protected Double a = 0d;
        protected Double b = 0d;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FloatPair extends Pair<Float, Float> {
        protected Float a = 0f;
        protected Float b = 0f;
    }
}
