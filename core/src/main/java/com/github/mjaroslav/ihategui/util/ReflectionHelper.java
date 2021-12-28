package com.github.mjaroslav.ihategui.util;

public class ReflectionHelper {
    public static Object createClassInstance(String classType) {
        try {
            return Class.forName(classType).newInstance();
        } catch (InstantiationException | ClassNotFoundException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
