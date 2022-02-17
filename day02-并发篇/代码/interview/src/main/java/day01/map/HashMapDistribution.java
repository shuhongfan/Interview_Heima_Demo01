package day01.map;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

// --add-opens java.base/java.util=ALL-UNNAMED
public class HashMapDistribution {

    public static void main(String[] args) throws IOException {
        Object value = new Object();
        Map<String, Object> words = Files.readAllLines(Path.of("words")).stream()
                .collect(Collectors.toMap(w -> w, w -> value));
        System.out.println(words.getClass());
        showDistribution(words);
    }

    private static void showDistribution(Map<String, Object> map) {
        try {
            Field tableField = HashMap.class.getDeclaredField("table");
            Field nextField = Class.forName("java.util.HashMap$Node").getDeclaredField("next");

            tableField.setAccessible(true);
            nextField.setAccessible(true);
            Object array = tableField.get(map);
            int length = Array.getLength(array);
            System.out.println("总的桶个数[" + length + "]");
            Map<Integer, AtomicInteger> result = new HashMap<>();
            for (int i = 0; i < length; i++) {
                Object node = Array.get(array, i);
                AtomicInteger c = result.computeIfAbsent(i, key -> new AtomicInteger());
                while (node != null) {
                    c.incrementAndGet();
                    node = nextField.get(node);
                }
            }
            Map.Entry maxEntry = null;
            int max = -1;
            HashMap<Integer, AtomicInteger> counting = new HashMap<>();
            for (Map.Entry<Integer, AtomicInteger> entry : result.entrySet()) {
                int value = entry.getValue().get();
                AtomicInteger c = counting.computeIfAbsent(value, k -> new AtomicInteger());
                c.incrementAndGet();
            }
            counting.forEach((k, v) -> {
                System.out.println(k + "个元素的桶个数[" + v + "]");
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
