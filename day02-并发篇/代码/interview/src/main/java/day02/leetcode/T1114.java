package day02.leetcode;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;


public class T1114 {
    public static void main(String[] args) {
        Foo foo = new Foo2();
        new Thread(foo::third).start();
        new Thread(foo::second).start();
        new Thread(foo::first).start();
    }

    interface Foo {
        public void first();

        public void second();

        public void third();
    }

    // 乐观锁实现
    static class Foo1 implements Foo {
        volatile int c = 1;

        public void first() {
            System.out.println("first");
            c = 2;
        }

        public void second() {
            while (c != 2) {

            }
            System.out.println("second");
            c = 3;
        }

        public void third() {
            while (c != 3) {

            }
            System.out.println("third");
        }
    }

    // 悲观锁实现
    static class Foo2 implements Foo {
        int c = 1;

        public void first() {
            synchronized (this) {
                System.out.println("first");
                c = 2;
                this.notifyAll();
            }
        }

        public void second() {
            synchronized (this) {
                while (c != 2) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("second");
                c = 3;
                this.notifyAll();
            }
        }

        public void third() {
            synchronized (this) {
                while (c != 3) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("third");
            }
        }
    }

    // 阻塞队列实现
    static class Foo3 implements Foo {
        private final SynchronousQueue<Integer> queue2 = new SynchronousQueue<>();
        private final SynchronousQueue<Integer> queue3 = new SynchronousQueue<>();

        @Override
        public void first() {
            try {
                System.out.println("first");
                queue2.put(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void second() {
            try {
                queue2.take();
                System.out.println("second");
                queue3.put(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void third() {
            try {
                queue3.take();
                System.out.println("third");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // 倒计时锁实现
    static class Foo4 implements Foo {
        private final CountDownLatch latch2 = new CountDownLatch(1);
        private final CountDownLatch latch3 = new CountDownLatch(1);

        @Override
        public void first() {
            System.out.println("first");
            latch2.countDown();
        }

        @Override
        public void second() {
            try {
                latch2.await();
                System.out.println("second");
                latch3.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void third() {
            try {
                latch3.await();
                System.out.println("third");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // park, unpark 乐观锁实现
    static class Foo5 implements Foo {
        private volatile int c = 1;
        private ConcurrentHashMap<Thread, Object> map = new ConcurrentHashMap<>();

        @Override
        public void first() {
            System.out.println("first");
            c = 2;
            for (Thread thread : map.keySet()) {
                LockSupport.unpark(thread);
            }
        }

        @Override
        public void second() {
            while (c != 2) {
                map.putIfAbsent(Thread.currentThread(), "2");
                LockSupport.park();
            }
            System.out.println("second");
            c = 3;
            for (Thread thread : map.keySet()) {
                LockSupport.unpark(thread);
            }
        }

        @Override
        public void third() {
            while (c != 3) {
                map.putIfAbsent(Thread.currentThread(), "3");
                LockSupport.park();
            }
            System.out.println("third");
        }
    }


}
