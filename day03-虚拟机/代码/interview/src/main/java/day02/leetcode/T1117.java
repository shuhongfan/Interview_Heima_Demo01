package day02.leetcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class T1117 {

    static interface H2O {
        public void hydrogen();
        public void oxygen();
    }

    // synchronized 实现
    /*
        思路:
        1) 打印 O 的线程开始只能 wait, 因 o 初始值是 0
        2) 打印 H 的线程刚开始有两个线程允许运行, 分别将 h-- 减为 0, 阻止后续打印 H 线程运行
        3) 开始打印 H 的两个线程, 还会将 o++ 变为 2, 这时唤醒打印 O 的线程
        4) 打印 O 的线程将 o-=2 阻止其他打印 O 线程运行, 并将 h+=2 允许两个打印 H 线程运行
     */
    static class H2OImpl2 implements H2O {

        int h = 2;
        int o = 0;

        @Override
        public void hydrogen() {
            synchronized (this) {
                while (h == 0) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print("H");
                h--;
                o++;
                this.notifyAll();
            }
        }

        @Override
        public void oxygen() {
            synchronized (this) {
                while (o != 2) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print("O");
                o-=2;
                h+=2;
                this.notifyAll();
            }
        }
    }

    // lock 实现
    static class H2OImpl3 implements H2O {

        int h = 2;
        int o = 0;

        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        @Override
        public void hydrogen() {
            lock.lock();
            try {
                while (h == 0) {
                    try {
                        condition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print("H");
                h--;
                o++;
                condition.signalAll();
            } finally {
                lock.unlock();
            }
        }

        @Override
        public void oxygen() {
            lock.lock();
            try {
                while (o != 2) {
                    try {
                        condition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print("O");
                o-=2;
                h+=2;
                condition.signalAll();
            } finally {
                lock.unlock();
            }
        }
    }

    // semaphore 实现
    static class H2OImpl1 implements H2O {
        Semaphore s1 = new Semaphore(2);
        Semaphore s2 = new Semaphore(0);
        @Override
        public void hydrogen() {
            try {
                s1.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.print("H");
            s2.release();
        }

        @Override
        public void oxygen() {
            try {
                s2.acquire(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.print("O");
            s1.release(2);
        }
    }


    public static void main(String[] args) throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        H2O h2o = new H2OImpl3();
        /*int n = 3;
        for (int i = 0; i < 3 * n; i++) {
            if (i % 3 == 0) {
                threads.add(new Thread(h2o::oxygen));
            } else {
                threads.add(new Thread(h2o::hydrogen));
            }
        }
        Collections.shuffle(threads);*/
        threads.add(new Thread(h2o::oxygen));
        threads.add(new Thread(h2o::oxygen));
        threads.add(new Thread(h2o::hydrogen));
        threads.add(new Thread(h2o::hydrogen));
        threads.add(new Thread(h2o::hydrogen));
        threads.add(new Thread(h2o::hydrogen));
        for (Thread thread : threads) {
            thread.start();
        }
    }

}
