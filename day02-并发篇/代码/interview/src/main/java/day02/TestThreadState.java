package day02;

import static day02.LoggerUtils.logger1;
import static day02.LoggerUtils.main;

public class TestThreadState {
    static final Object LOCK = new Object();
    public static void main(String[] args) throws InterruptedException {
        testWaiting();
    }

    private static void testWaiting() {
        Thread t2 = new Thread(() -> {
            synchronized (LOCK) {
                logger1.debug("before waiting"); // 1
                try {
                    LOCK.wait(); // 3
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"t2");

        t2.start();
        main.debug("state: {}", t2.getState()); // 2
        synchronized (LOCK) {
            main.debug("state: {}", t2.getState()); // 4
            LOCK.notify(); // 5
            main.debug("state: {}", t2.getState()); // 6
        }
        main.debug("state: {}", t2.getState()); // 7
    }

    private static void testBlocked() {
        Thread t2 = new Thread(() -> {
            logger1.debug("before sync"); // 3
            synchronized (LOCK) {
                logger1.debug("in sync"); // 4
            }
        },"t2");

        t2.start();
        main.debug("state: {}", t2.getState()); // 1
        synchronized (LOCK) {
            main.debug("state: {}", t2.getState()); // 2
        }
        main.debug("state: {}", t2.getState()); // 5
    }

    private static void testNewRunnableTerminated() {
        Thread t1 = new Thread(() -> {
            logger1.debug("running..."); // 3
        },"t1");

        main.debug("state: {}", t1.getState()); // 1
        t1.start();
        main.debug("state: {}", t1.getState()); // 2

        main.debug("state: {}", t1.getState()); // 4
    }
}
