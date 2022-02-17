package day03.loader;

import jdk.internal.loader.BuiltinClassLoader;
import jdk.internal.loader.ClassLoaders;
import jdk.internal.loader.URLClassPath;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class TestAppClassLoader {
    public static void main(String[] args) throws Exception{
        ClassLoader loader = newAppClassLoader();
        System.out.println(loader.getParent());
        /*Class<?> clazz1 = Class.forName("day03.loader.Student", true, loader);
        Class<?> clazz2 = Class.forName("day03.loader.Student", true, loader);
        Class<?> clazz3 = Student.class;
        clazz1.getDeclaredConstructor().newInstance();
        System.out.println(new Student());
        System.out.println(clazz1 == clazz2);
        System.out.println(clazz1 == clazz3);*/

        Class.forName("day03.loader.Student", true, loader);
    }

    public static ClassLoader newAppClassLoader() {
        try {
            Class<?> clazz = Class.forName("jdk.internal.loader.ClassLoaders$AppClassLoader");
            Constructor<?> cons = clazz.getDeclaredConstructor(BuiltinClassLoader.class, URLClassPath.class);
            cons.setAccessible(true);
            ClassLoader loader = ClassLoaders.platformClassLoader();
            ClassLoader o = (ClassLoader) cons.newInstance(null, getUrlClassPath());
            return o;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static URLClassPath getUrlClassPath() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Constructor<URLClassPath> cons = URLClassPath.class.getDeclaredConstructor(String.class, boolean.class);
        cons.setAccessible(true);
        return cons.newInstance(System.getProperty("java.class.path"), false);
    }
}
