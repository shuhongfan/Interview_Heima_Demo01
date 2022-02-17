package day01.list;

import org.openjdk.jol.info.ClassLayout;
import org.springframework.util.StopWatch;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static day01.list.TestArrayList.length;
import static day01.sort.Utils.randomArray;

@SuppressWarnings("all")
public class ArrayListVsLinkedList {
    public static void main(String[] args) {
        int n = 1000;
        int insertIndex = n;
        for (int i = 0; i < 1; i++) {
            int[] array = randomArray(n);
            List<Integer> list1 = Arrays.stream(array).boxed().collect(Collectors.toList());
            LinkedList<Integer> list2 = new LinkedList<>(list1);

//            randomAccess(list1, list2, n / 2);
//            addFirst(list1,list2);
//            addMiddle(list1, list2, n / 2);
//            addLast(list1,list2);
            arrayListSize((ArrayList<Integer>) list1);
            linkedListSize(list2);
        }
    }

    static void linkedListSize(LinkedList<Integer> list) {
        try {
            long size = 0;
            ClassLayout layout = ClassLayout.parseInstance(list);
//            System.out.println(layout.toPrintable());
            size += layout.instanceSize();
            Field firstField = LinkedList.class.getDeclaredField("first");
            firstField.setAccessible(true);
            Object first = firstField.get(list);
//            System.out.println(ClassLayout.parseInstance(first).toPrintable());
            long nodeSize = ClassLayout.parseInstance(first).instanceSize();
            size += (nodeSize * (list.size() + 2));
            long elementSize = ClassLayout.parseInstance(list.getFirst()).instanceSize();
            System.out.println("LinkedList size:[" + size + "],per Node size:[" + nodeSize + "],per Element size:[" + elementSize * list.size() + "]");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void arrayListSize(ArrayList<Integer> list) {
        try {
            long size = 0;
            ClassLayout layout = ClassLayout.parseInstance(list);
//            System.out.println(layout.toPrintable());
            size += layout.instanceSize();
            Field elementDataField = ArrayList.class.getDeclaredField("elementData");
            elementDataField.setAccessible(true);
            Object elementData = elementDataField.get(list);
//            System.out.println(ClassLayout.parseInstance(elementData).toPrintable());
            size += ClassLayout.parseInstance(elementData).instanceSize();
            long elementSize = ClassLayout.parseInstance(list.get(0)).instanceSize();
            System.out.println("ArrayList size:[" + size + "],array length:[" + length(list) + "],per Element size:[" + elementSize * list.size() + "]");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void randomAccess(List<Integer> list1, LinkedList<Integer> list2, int mid) {
        StopWatch sw = new StopWatch();
        sw.start("ArrayList");
        list1.get(mid);
        sw.stop();

        sw.start("LinkedList");
        list2.get(mid);
        sw.stop();

        System.out.println(sw.prettyPrint());
    }

    private static void addMiddle(List<Integer> list1, LinkedList<Integer> list2, int mid) {
        StopWatch sw = new StopWatch();
        sw.start("ArrayList");
        list1.add(mid, 100);
        sw.stop();

        sw.start("LinkedList");
        list2.add(mid, 100);
        sw.stop();

        System.out.println(sw.prettyPrint());
    }

    private static void addFirst(List<Integer> list1, LinkedList<Integer> list2) {
        StopWatch sw = new StopWatch();
        sw.start("ArrayList");
        list1.add(0, 100);
        sw.stop();

        sw.start("LinkedList");
        list2.addFirst(100);
        sw.stop();

        System.out.println(sw.prettyPrint());
    }

    private static void addLast(List<Integer> list1, LinkedList<Integer> list2) {
        StopWatch sw = new StopWatch();
        sw.start("ArrayList");
        list1.add(100);
        sw.stop();

        sw.start("LinkedList");
        list2.add(100);
        sw.stop();

        System.out.println(sw.prettyPrint());
    }
}
