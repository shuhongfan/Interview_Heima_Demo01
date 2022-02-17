package day01.map;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

// --add-opens java.base/java.util=ALL-UNNAMED
@SuppressWarnings("all")
public class HashMapVsHashtable {
    public static void main(String[] args) throws IOException, NoSuchFieldException, IllegalAccessException, ClassNotFoundException {
        List<String> words = Files.readAllLines(Path.of("words"));
        int size = (int) Math.pow(2, 19);
        int pShift = 5;
        Object value = new Object();
        HashMap<String, Object> map = new HashMap<>(size);
        Hashtable<String, Object> hashtable = new Hashtable<>(393215);
        for (String word : words) {
            map.put(word, value);
            hashtable.put(word, value);
        }
        hashMap(pShift, map);
        hashtable(pShift, hashtable);
    }


    private static void hashtable(int pShift, Hashtable<String, Object> hashtable) throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException {
        Field tableField = Hashtable.class.getDeclaredField("table");
        Field nextField = Class.forName("java.util.Hashtable$Entry").getDeclaredField("next");
        tableField.setAccessible(true);
        nextField.setAccessible(true);
        Object array = tableField.get(hashtable);
        int length = Array.getLength(array);
        System.out.printf("(%d)------------------------------------------->%n", length);
        Map<Integer, AtomicInteger> result = new HashMap<>();
        for (int i = 0; i < length; i++) {
            Object node = Array.get(array, i);
            AtomicInteger c = result.computeIfAbsent(i >>> 19 - pShift, key -> new AtomicInteger());
            while (node != null) {
                c.incrementAndGet();
                node = nextField.get(node);
            }
        }
        LongAdder sum = new LongAdder();
        result.forEach((k, v) -> {
            int star = (int) Math.round(v.get() / 1000.0);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < star; i++) {
                sb.append("*");
            }
            System.out.println(String.format("%02d", k) + " " + sb.toString());
            sum.add(v.get());
        });
        System.out.println("------------------------------------------->");
        System.out.println(sum);
    }

    private static void hashMap(int pShift, HashMap<String, Object> map) {
        try {
            Field tableField = HashMap.class.getDeclaredField("table");
            Field nextField = Class.forName("java.util.HashMap$Node").getDeclaredField("next");

            tableField.setAccessible(true);
            nextField.setAccessible(true);
            Object array = tableField.get(map);
            int length = Array.getLength(array);
            System.out.printf("(%d)------------------------------------------->%n", length);
            Map<Integer, AtomicInteger> result = new HashMap<>();
            for (int i = 0; i < length; i++) {
                Object node = Array.get(array, i);
                AtomicInteger c = result.computeIfAbsent(i >>> 19 - pShift, key -> new AtomicInteger());
                while (node != null) {
                    c.incrementAndGet();
                    node = nextField.get(node);
                }
            }
            LongAdder sum = new LongAdder();
            result.forEach((k, v) -> {
                int star = (int) Math.round(v.get() / 1000.0);
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < star; i++) {
                    sb.append("*");
                }
                System.out.println(String.format("%02d", k) + " " + sb.toString());
                sum.add(v.get());
            });
            System.out.println("------------------------------------------->");
            System.out.println(sum);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
