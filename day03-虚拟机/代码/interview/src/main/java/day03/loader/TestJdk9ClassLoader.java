package day03.loader;

import jdk.internal.loader.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

// --add-opens java.base/jdk.internal.loader=ALL-UNNAMED
public class TestJdk9ClassLoader {
    public static void main(String[] args) {
        ClassLoader appLoader = TestJdk9ClassLoader.class.getClassLoader();
        System.out.println(appLoader + "============>");
        showPackages(appLoader);

        ClassLoader platformLoader = appLoader.getParent();
        System.out.println(platformLoader + "============>");
        showPackages(platformLoader);

        ClassLoader bootLoader = getBootLoader(platformLoader);
        System.out.println(bootLoader + "============>");
        showPackages(bootLoader);
    }

    private static ClassLoader getBootLoader(ClassLoader platformLoader) {
        try {
            Field parent = BuiltinClassLoader.class.getDeclaredField("parent");
            parent.setAccessible(true);
            return (ClassLoader) parent.get(platformLoader);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void showPackages(ClassLoader loader) {
        try {
            Field nameToModule = BuiltinClassLoader.class.getDeclaredField("nameToModule");
            nameToModule.setAccessible(true);

            Map<String, Object> map = (Map<String, Object>) nameToModule.get(loader);
            List<String> list = new ArrayList<>(map.keySet());
            list.sort(Comparator.naturalOrder());
            for (int i = 0; i < list.size(); i++) {
                System.out.print(list.get(i));
                System.out.print("\t");
                if ((i + 1) % 6 == 0 || i == list.size() - 1) {
                    System.out.println();
                }
            }
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
