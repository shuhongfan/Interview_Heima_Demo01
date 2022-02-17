package day01.pattern;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ShallowCopy {

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


    static class Student implements Cloneable {
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

        @Override
        protected Student clone() {
            try {
                return (Student) super.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
