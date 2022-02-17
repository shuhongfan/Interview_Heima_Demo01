package day02.threadsafe;

import day02.LoggerUtils;

import java.util.concurrent.CountDownLatch;

// 原子性例子

/**

 t1 10
 0: getstatic
                 t2
                 0: getstatic 10
                 3: iconst_5
                 4: isub
                 5: putstatic
                    5
 3: iconst_5
 4: iadd
 5: putstatic
 15



 */
public class AddAndSubtract {

    static volatile int balance = 10;

    public static void subtract() {
        int b = balance;
        b -= 5;
        balance = b;
    }

    public static void add() {
        int b = balance;
        b += 5;
        balance = b;
    }

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(2);
        new Thread(() -> {
            subtract();
            latch.countDown();
        }).start();
        new Thread(() -> {
            add();
            latch.countDown();
        }).start();
        latch.await();
        LoggerUtils.get().debug("{}", balance);
    }
}
