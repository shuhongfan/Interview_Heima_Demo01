package day03.loader;

public class Student {
    static int a = 0x77;
    static {
        System.out.println("Student.class init");
    }
    static int b = 0x88;

    static final int c = 0x99;
    static final int m = Short.MAX_VALUE + 1;
    static final Object n = new Object();

    int d = 0x55;
    int e = 0x66;
}
