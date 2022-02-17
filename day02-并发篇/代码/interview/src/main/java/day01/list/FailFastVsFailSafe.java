package day01.list;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class FailFastVsFailSafe {
    // fail-fast 一旦发现遍历的同时其它人来修改，则立刻抛异常
    // fail-safe 发现遍历的同时其它人来修改，应当能有应对策略，例如牺牲一致性来让整个遍历运行完成

    private static void failFast() {
        ArrayList<Student> list = new ArrayList<>();
        list.add(new Student("A"));
        list.add(new Student("B"));
        list.add(new Student("C"));
        list.add(new Student("D"));
        for (Student student : list) {
            System.out.println(student);
        }
        System.out.println(list);
    }

    private static void failSafe() {
        CopyOnWriteArrayList<Student> list = new CopyOnWriteArrayList<>();
        list.add(new Student("A"));
        list.add(new Student("B"));
        list.add(new Student("C"));
        list.add(new Student("D"));
        for (Student student : list) {
            System.out.println(student);
        }
        System.out.println(list);
    }

    static class Student {
        String name;

        public Student(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

    public static void main(String[] args) {
        failSafe();
    }
}
