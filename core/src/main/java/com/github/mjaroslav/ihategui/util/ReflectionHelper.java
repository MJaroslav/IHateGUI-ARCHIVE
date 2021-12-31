package com.github.mjaroslav.ihategui.util;

import lombok.val;

import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

public class ReflectionHelper {
    public static Set<Class<?>> getClassesInPackage(String packageName, boolean tree) throws Exception {
        packageName = packageName.replace('.', '/');
        val result = new HashSet<Class<?>>();
        val URLs = Thread.currentThread().getContextClassLoader().getResources(packageName);
        while (URLs.hasMoreElements())
            getClassesInPackage(result, URLs.nextElement(), packageName, tree);
        return result;
    }

    private static void getClassesInPackage(Set<Class<?>> result, URL packageURL, String packageName, boolean tree) throws Exception {
        val names = new ArrayList<String>();

        if (packageURL == null)
            return;

        if (packageURL.getProtocol().equals("jar")) {
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
        for (val name : names.stream().map(name -> name.replace(".class", "")).collect(Collectors.toList()))
            result.add(Class.forName(name));
    }

    public static Object createClassInstance(String clazz) throws Exception {
        return Class.forName(clazz).newInstance();
    }
}