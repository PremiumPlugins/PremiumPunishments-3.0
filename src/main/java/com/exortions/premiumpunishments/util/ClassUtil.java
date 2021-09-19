package com.exortions.premiumpunishments.util;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClassUtil {

    public static Class<?>[] getClassesInPackage(String packageName) {
        File directory = getPackageDirectory(packageName);
        if (!directory.exists()) throw new IllegalArgumentException("Could not get directory resource for package " + packageName + ".");

        return getClassesInPackage(packageName, directory);
    }

    private static Class<?>[] getClassesInPackage(String packageName, File directory) {
        List<Class<?>> classes = new ArrayList<>();
        for (String filename : Objects.requireNonNull(directory.list())) {
            if (filename.endsWith(".class")) {
                String classname = buildClassname(packageName, filename);
                try {
                    classes.add(Class.forName(classname));
                } catch (ClassNotFoundException e) {
                    System.err.println("Error creating class " + classname);
                }
            }
        }
        return classes.toArray(new Class[0]);
    }

    private static String buildClassname(String packageName, String filename) {
        return packageName + '.' + filename.replace(".class", "");
    }

    private static File getPackageDirectory(String packageName) {
        ClassLoader cld = Thread.currentThread().getContextClassLoader();
        if (cld == null) throw new IllegalStateException("Can't get class loader.");

        URL resource = cld.getResource(packageName.replace('.', '/'));
        if (resource == null) throw new RuntimeException("Package " + packageName + " not found on classpath.");

        return new File(resource.getFile());
    }

}
