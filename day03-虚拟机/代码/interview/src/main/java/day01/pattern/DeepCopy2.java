package day01.pattern;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DeepCopy2 {

    public static void main(String[] args) throws Exception {
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

    static class Student{
        private String name;
        private int age;
        private Date birthday;

        public Student() {
        }

        public Student(String name, int age, Date birthday) {
            this.name = name;
            this.age = age;
            this.birthday = birthday;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        public Date getBirthday() {
            return birthday;
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
                ObjectMapper om = new ObjectMapper();
                String json = om.writeValueAsString(this);
                return om.readValue(json, Student.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
