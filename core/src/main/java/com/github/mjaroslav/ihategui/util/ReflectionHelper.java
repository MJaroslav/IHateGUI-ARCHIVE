package com.github.mjaroslav.ihategui.util;

import com.github.mjaroslav.ihategui.view.ViewLoader;

public class ReflectionHelper {
    public static Object createClassInstance(String classType) {
        try {
            return Class.forName(classType).newInstance();
        } catch (InstantiationException | ClassNotFoundException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object createModelElement(String classType) {
        Class<?> clazz = ViewLoader.tryResolveElementByAlias(classType);
        if (clazz == null)
            return createClassInstance(classType);
        else {
            try {
                return clazz.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
