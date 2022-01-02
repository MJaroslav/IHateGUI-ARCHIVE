package com.github.mjaroslav.ihategui.util;

import com.github.mjaroslav.ihategui.api.view.Ignore;
import lombok.val;
import lombok.var;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

public class ReflectionHelper {
    private static final String FILE_PROTOCOL = "file";
    private static final String JAR_PROTOCOL = "jar";
    private static final String CLASS_EXTENSION = ".class";
    private static final int FILE_PROTOCOL_LENGTH = FILE_PROTOCOL.length();
    private static final int CLASS_EXTENSION_LENGTH = CLASS_EXTENSION.length();

    @NotNull
    public static Set<Class<?>> getClassesInPackage(@NotNull String packageName, boolean tree) throws Exception {
        packageName = packageName.replace('.', '/');
        val result = new HashSet<Class<?>>();
        val URLs = Thread.currentThread().getContextClassLoader().getResources(packageName);
        while (URLs.hasMoreElements())
            getClassesInPackage(result, URLs.nextElement(), packageName, tree);
        return result;
    }

    private static void getClassesInPackage(@NotNull Set<Class<?>> result, @NotNull URL packageURL,
                                            @NotNull String packageName, boolean tree) throws Exception {
        val classes = new ArrayList<Class<?>>();
        val protocol = packageURL.getProtocol();
        if (protocol.equals(JAR_PROTOCOL)) {
            val jarFileName = URLDecoder.decode(packageURL.getFile(), StandardCharsets.UTF_8.toString());
            val jarFile = new JarFile(jarFileName.substring(FILE_PROTOCOL_LENGTH + 1, jarFileName.indexOf('!')));
            val jarEntries = jarFile.entries();
            val marker = packageName.split("/").length + 1; // Package slashes count + one between package and class names
            String entryName;
            while (jarEntries.hasMoreElements()) {
                entryName = jarEntries.nextElement().getName();
                if (entryName.startsWith(packageName) && (tree || entryName.split("/").length == marker)
                        && entryName.endsWith(CLASS_EXTENSION))
                    classes.add(getClass(StringUtils.sub(entryName.replace('/', '.'), -CLASS_EXTENSION_LENGTH)));
            }
        } else if (protocol.equals(FILE_PROTOCOL)) {
            val folder = new URI(packageURL.toString()).getPath();
            val files = tree ? Files.walk(Paths.get(folder)) : Files.list(Paths.get(folder));
            val finalPackageName = packageName;
            files.filter(Files::isRegularFile)
                    .map(Path::toString)
                    .filter(file -> file.endsWith(CLASS_EXTENSION))
                    .forEach(file -> {
                        file = file.substring(file.indexOf(finalPackageName));
                        classes.add(getClass(StringUtils.sub(file.replace('/', '.'), -CLASS_EXTENSION_LENGTH)));
                    });
        }
        result.addAll(classes);
    }

    @NotNull
    public static Class<?> getClass(@NotNull String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Nullable
    public static Class<?> getClassNullable(@NotNull String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException ignored) {
            return null;
        }
    }

    @NotNull
    public static Object createClassInstance(@NotNull String className) {
        return createClassInstance(className, true, false, false);
    }

    @Nullable
    public static Object createClassInstanceNullable(@NotNull String className) {
        return createClassInstance(className, false, false, false);
    }

    public static Object createClassInstance(@NotNull Class<?> clazz) {
        return createClassInstance(clazz, true, false, false);
    }

    @Nullable
    public static Object createClassInstanceNullable(@NotNull Class<?> clazz) {
        return createClassInstance(clazz, false, false, false);
    }

    @UnknownNullability
    public static Object createClassInstance(@NotNull Class<?> clazz, boolean throwException, boolean printException,
                                             boolean returnException) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            if (throwException)
                throw new RuntimeException(e);
            else if (printException)
                e.printStackTrace();
            return returnException ? e : null;
        }
    }

    @UnknownNullability
    public static Object createClassInstance(@NotNull String className, boolean throwException, boolean printException,
                                             boolean returnException) {
        try {
            return Class.forName(className).newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            if (throwException)
                throw new RuntimeException(e);
            else if (printException)
                e.printStackTrace();
            return returnException ? e : null;
        }
    }

    public static Set<Field> getDeclaredFields(@NotNull Class<?> clazz) {
        val result = new HashSet<>(Arrays.asList(clazz.getDeclaredFields()));
        var parent = clazz.getSuperclass();
        while (parent != Object.class) {
            result.addAll(Arrays.asList(parent.getDeclaredFields()));
            parent = parent.getSuperclass();
        }
        return result;
    }

    public static boolean isStatic(Field field) {
        return Modifier.isStatic(field.getModifiers());
    }

    @NotNull
    public static Set<Field> getNotIgnoredViewLoaderFields(@NotNull Class<?> clazz) {
        return getDeclaredFields(clazz).stream()
                .filter(field -> !field.isAnnotationPresent(Ignore.class) && !isStatic(field)).collect(Collectors.toSet());
    }
}