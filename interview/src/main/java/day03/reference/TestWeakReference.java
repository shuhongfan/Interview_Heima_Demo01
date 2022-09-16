package day03.reference;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

public class TestWeakReference {

    public static void main(String[] args) {
        MyWeakMap map = new MyWeakMap();
        map.put(0, new String("a"), "1");
        map.put(1, "b", "2");
        map.put(2, new String("c"), "3");
        map.put(3, new String("d"), "4");
        System.out.println(map);

        System.gc();
        System.out.println(map.get("a"));
        System.out.println(map.get("b"));
        System.out.println(map.get("c"));
        System.out.println(map.get("d"));
        System.out.println(map);
        map.clean();
        System.out.println(map);
    }

    // 模拟 ThreadLocalMap 的内存泄漏问题以及一种解决方法
    static class MyWeakMap {
        static ReferenceQueue<Object> queue = new ReferenceQueue<>();
        static class Entry extends WeakReference<String> {
            String value;

            public Entry(String key, String value) {
                super(key, queue);
                this.value = value;
            }
        }
        public void clean() {
            Object ref;
            while ((ref = queue.poll()) != null) {
                System.out.println(ref);
                for (int i = 0; i < table.length; i++) {
                    if(table[i] == ref) {
                        table[i] = null;
                    }
                }
            }
        }

        Entry[] table = new Entry[4];

        public void put(int index, String key, String value) {
            table[index] = new Entry(key, value);
        }

        public String get(String key) {
            for (Entry entry : table) {
                if (entry != null) {
                    String k = entry.get();
                    if (k != null && k.equals(key)) {
                        return entry.value;
                    }
                }
            }
            return null;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            for (Entry entry : table) {
                if (entry != null) {
                    String k = entry.get();
                    sb.append(k).append(":").append(entry.value).append(",");
                }
            }
            if (sb.length() > 1) {
                sb.deleteCharAt(sb.length() - 1);
            }
            sb.append("]");
            return sb.toString();
        }
    }
}
