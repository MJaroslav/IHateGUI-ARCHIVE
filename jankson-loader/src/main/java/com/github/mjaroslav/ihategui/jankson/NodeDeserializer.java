package com.github.mjaroslav.ihategui.jankson;

import blue.endless.jankson.JsonArray;
import blue.endless.jankson.JsonObject;
import blue.endless.jankson.JsonPrimitive;
import blue.endless.jankson.api.DeserializationException;
import blue.endless.jankson.api.DeserializerFunction;
import blue.endless.jankson.api.Marshaller;
import com.github.mjaroslav.ihategui.api.model.Container;
import com.github.mjaroslav.ihategui.api.model.Node;
import com.github.mjaroslav.ihategui.util.ReflectionHelper;
import com.github.mjaroslav.ihategui.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
final class NodeDeserializer implements DeserializerFunction<JsonObject, Node> {
    public static final String[] EXTRA_IGNORE_NAMES = {"nodes", "controller", "imports", "class"};

    public final Map<String, Class<?>> importedNodes = new HashMap<>();

    public void nodeImport(@NotNull String importLine) throws Exception {
        if (importLine.indexOf(' ') > 0) {
            val pronoun = StringUtils.subAfter(importLine, ' ');
            val clazz = ReflectionHelper.getClassNullable(StringUtils.subBefore(importLine, ' '));
            if (clazz == null)
                throw new IllegalArgumentException(String.format("Class for import \"%s\" not found", importLine));
            nodeImport(pronoun, clazz);
        } else if (importLine.endsWith("*")) {
            val classes = ReflectionHelper.getClassesInPackage(StringUtils.sub(importLine, -2), false);
            for (Class<?> clazz : classes) {
                val pronoun = clazz.getSimpleName();
                nodeImport(pronoun, clazz);
            }
        } else {
            val clazz = ReflectionHelper.getClass(importLine);
            val pronoun = clazz.getSimpleName();
            nodeImport(pronoun, clazz);
        }
    }

    public void nodeImport(@NotNull String pronoun, @NotNull Class<?> clazz) {
        if (importedNodes.containsKey(pronoun))
            throw new IllegalArgumentException(
                    String.format("This name \"%s\" is taken by \"%s\"! add a pronoun separated by a space", pronoun, importedNodes.get(pronoun).getName()));
        else importedNodes.put(pronoun, clazz);
    }

    private Class<?> getClass(@NotNull String className) {
        return importedNodes.containsKey(className) ? importedNodes.get(className) : ReflectionHelper.getClass(className);
    }

    @Override
    public Node apply(JsonObject object, Marshaller m) throws DeserializationException {
        val nodeTypeClass = object.get(String.class, "class");
        if (nodeTypeClass == null)
            throw new DeserializationException("Node type class didn't set");
        val nodeInstance = ReflectionHelper.createClassInstance(getClass(nodeTypeClass), false, false, true);
        if (nodeInstance instanceof Exception)
            throw new DeserializationException("Can't create node instance", (Exception) nodeInstance);
        if (!(nodeInstance instanceof Node))
            throw new DeserializationException("Created instance is not Node child type");
        val node = (Node) nodeInstance;

        val extraIgnoreNames = new ArrayList<>(Arrays.asList(EXTRA_IGNORE_NAMES));

        try {
            for (val field : ReflectionHelper.getNotIgnoredViewLoaderFields(node.getClass())) {
                if (object.containsKey(field.getName())) {
                    field.setAccessible(true);
                    field.set(node, m.marshall(field.getType(), object.get(field.getName())));

                }
                extraIgnoreNames.add(field.getName());
            }
        } catch (IllegalAccessException e) {
            throw new DeserializationException(e);
        }

        if (node instanceof Container) {
            val container = (Container) node;
            val nodesArray = (JsonArray) object.get("nodes");
            if (nodesArray != null)
                for (val element : nodesArray) {
                    val childNode = m.marshall(Node.class, element);
                    container.addNode(childNode);
                }
        }

        object.entrySet().stream().filter(entry -> !extraIgnoreNames.contains(entry.getKey()))
                .forEach(entry -> node.setAttribute(entry.getKey(), ((JsonPrimitive) entry.getValue()).getValue()));

        return node;
    }
}
