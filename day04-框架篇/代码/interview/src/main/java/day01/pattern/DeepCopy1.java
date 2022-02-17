package day01.pattern;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DeepCopy1 {
    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date birthday = sdf.parse("1999-10-11");
        Student s1 = new Student("张三", 18, birthday);
        Student s2 = s1.clone();
        System.out.println("before modify...");
        System.out.println(s1);
        System.out.println(s2);

        System.out.println("after modify...");
        s2.name = "李四";
        s2.birthday.setDate(12);
        System.out.println(s1);
        System.out.println(s2);
    }

    static class Student implements Serializable {
        private String name;
        private int age;
        private Date birthday;

        public Student(String name, int age, Date birthday) {
            this.name = name;
            this.age = age;
            this.birthday = birthday;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", birthday=" + birthday +
                    '}';
        }

        public Student clone() {
            try {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(bos);
                oos.writeObject(this);
                ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
                return (Student) ois.readObject();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
