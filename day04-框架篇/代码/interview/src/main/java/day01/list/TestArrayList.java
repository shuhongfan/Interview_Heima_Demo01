package day01.list;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

// --add-opens java.base/java.util=ALL-UNNAMED
public class TestArrayList {
    public static void main(String[] args) {
        System.out.println(arrayListGrowRule(30));
//        testAddAllGrowEmpty();
        testAddAllGrowNotEmpty();
    }

    private static List<Integer> arrayListGrowRule(int n) {
        List<Integer> list = new ArrayList<>();
        int init = 0;
        list.add(init);
        if (n >= 1) {
            init = 10;
            list.add(init);
        }
        for (int i = 1; i < n; i++) {
            init += (init) >> 1;
            list.add(init);
        }
        return list;
    }

    private static void testAddAllGrowEmpty() {
        ArrayList<Integer> list = new ArrayList<>();
//        list.addAll(List.of(1, 2, 3));
//        list.addAll(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11));
        System.out.println(length(list));
    }

    private static void testAddAllGrowNotEmpty() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
//        list.addAll(List.of(1, 2, 3));
        list.addAll(List.of(1, 2, 3, 4, 5, 6));
        System.out.println(length(list));
    }

    public static int length(ArrayList<Integer> list) {
        try {
            Field field = ArrayList.class.getDeclaredField("elementData");
            field.setAccessible(true);
            return ((Object[]) field.get(list)).length;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


}
