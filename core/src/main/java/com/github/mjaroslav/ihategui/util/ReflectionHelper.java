package com.github.mjaroslav.ihategui.util;

public class ReflectionHelper {
    public static Object createClassInstance(String classType) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        return Class.forName(classType).newInstance();
    }
}
