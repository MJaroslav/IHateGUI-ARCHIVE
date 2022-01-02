package com.github.mjaroslav.ihategui.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StringUtils {
    public static boolean isEmpty(@Nullable String str) {
        return str == null || str.isEmpty();
    }

    @NotNull
    public static String subBefore(@NotNull String str, int chr) {
        return str.substring(0, str.indexOf(chr));
    }

    @NotNull
    public static String subAfter(@NotNull String str, int chr) {
        return str.substring(str.indexOf(chr) + 1);
    }

    @NotNull
    public static String subBeforeLast(@NotNull String str, int chr) {
        return str.substring(0, str.lastIndexOf(chr) + 1);
    }

    @NotNull
    public static String subAfterLast(@NotNull String str, int chr) {
        return str.substring(str.lastIndexOf(chr) + 1);
    }

    @NotNull
    public static String sub(@NotNull String str, int index) {
        if (index > -1)
            return str.substring(index);
        else return str.substring(0, str.length() + index);
    }
}
