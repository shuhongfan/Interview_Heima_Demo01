package day03;

import org.openjdk.jol.info.ClassLayout;

import java.nio.charset.StandardCharsets;

// 演示对象的内存估算
public class TestOomTooManyObject {
    public static void main(String[] args) {
        // 对象本身内存
        long a = ClassLayout.parseInstance(new Product()).instanceSize();
        System.out.println(a);
        // 一个字符串占用内存
        String name = "联想小新Air14轻薄本 英特尔酷睿i5 14英寸全面屏学生笔记本电脑(i5-1135G7 16G 512G MX450独显 高色域)银";
        long b = ClassLayout.parseInstance(name).instanceSize();
        System.out.println(b);
        String desc = "【全金属全面屏】学生商务办公，全新11代处理器，MX450独显，100%sRGB高色域，指纹识别，快充（更多好货）";
        long c = ClassLayout.parseInstance(desc).instanceSize();
        System.out.println(c);
        System.out.println(16 + name.getBytes(StandardCharsets.UTF_8).length);
        System.out.println(16 + desc.getBytes(StandardCharsets.UTF_8).length);
        // 一个对象估算的内存
        long avg = a + b + c + 16 + name.getBytes(StandardCharsets.UTF_8).length + 16 + desc.getBytes(StandardCharsets.UTF_8).length;
        System.out.println(avg);
        // ArrayList 24, Object[] 16 共 40
        System.out.println((1_000_000 * avg + 40) / 1024 / 1024 + "Mb");
    }

    static public class Product {
        private int id;
        private String name;
        private int price;
        private String desc;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}
