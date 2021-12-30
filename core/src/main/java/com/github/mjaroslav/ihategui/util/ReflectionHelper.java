package com.github.mjaroslav.ihategui.util;

import lombok.val;

import java.net.URI;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

public class ReflectionHelper {
    public static List<Class<?>> getClassesInPackage(String packageName, boolean tree) throws Exception {
        val classLoader = Thread.currentThread().getContextClassLoader();
        val names = new ArrayList<String>();

        packageName = packageName.replace('.', '/');
        val packageURL = classLoader.getResource(packageName);

        if (Objects.requireNonNull(packageURL).getProtocol().equals("jar")) {
            val jarFileName = URLDecoder.decode(packageURL.getFile(), "UTF-8");
            val jarFile = new JarFile(jarFileName.substring(5, jarFileName.indexOf('!')));
            val jarEntries = jarFile.entries();
            val marker = packageName.split("/").length + 1;
            String entryName;
            while (jarEntries.hasMoreElements()) {
                entryName = jarEntries.nextElement().getName();
                if (entryName.startsWith(packageName) && (tree || entryName.split("/").length == marker) && entryName.endsWith(".class"))
                    names.add(entryName.replace('/', '.'));
            }
        } else {
            val folder = new URI(packageURL.toString()).getPath();
            val files = tree ? Files.walk(Paths.get(folder)) : Files.list(Paths.get(folder));
            val finalPackageName = packageName;
            files.filter(Files::isRegularFile)
                    .map(Path::toString)
                    .filter(file -> file.endsWith(".class"))
                    .forEach(file -> {
                        file = file.substring(file.indexOf(finalPackageName));
                        names.add(file.replace('/', '.'));
                    });
        }
        val result = new ArrayList<Class<?>>();
        for (val name : names.stream().map(name -> name.replace(".class", "")).collect(Collectors.toList()))
            result.add(Class.forName(name));
        return result;
    }

    public static Object createClassInstance(String clazz) throws Exception {
        return Class.forName(clazz).newInstance();
    }
}