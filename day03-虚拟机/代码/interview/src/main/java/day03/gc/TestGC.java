package day03.gc;

import java.io.IOException;

// -Xmx20m -Xms20m -Xmn10m -XX:+UseSerialGC -XX:+PrintGCDetails
public class TestGC {
    static int _1MB = 1024 * 1024 - 16;
    static int _2MB = 2 * 1024 * 1024 - 16;
    static int _3MB = 3 * 1024 * 1024 - 16;
    static int _4MB = 4 * 1024 * 1024 - 16;
    static int _5MB = 5 * 1024 * 1024 - 16;
    static int _a = 1_342_256 ;

    public static void main(String[] args) throws IOException {
        byte[] b1 = new byte[_a];
        byte[] b2 = new byte[_5MB];
        byte[] b3 = new byte[_5MB];
//        byte[] b3 = new byte[_5MB];
//        byte[] b2 = new byte[_1MB];
//        byte[] b3 = new byte[_1MB];
//        byte[] b4 = new byte[_1MB];
//        byte[] b5 = new byte[_1MB];
//        byte[] b6 = new byte[_1MB];
//        byte[] b7 = new byte[_1MB];
//        byte[] b8 = new byte[_1MB];
        System.in.read();
    }
}
