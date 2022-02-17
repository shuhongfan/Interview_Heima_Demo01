package day02.leetcode;

public class T1115 {
    public static void main(String[] args) {
        FooBar fb = new FooBar2(5);
        new Thread(fb::foo).start();
        new Thread(fb::bar).start();
    }

    interface FooBar {
        public void foo();
        public void bar();
    }

    // 悲观锁版本
    static class FooBar1 implements FooBar {
        private final int n;
        private boolean flag;

        public FooBar1(int n) {
            this.n = n;
        }

        public void foo() {
            for (int i = 0; i < n; i++) {
                synchronized (this) {
                    while (flag) {
                        try {
                            this.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("foo");
                    flag = true;
                    this.notify();
                }
            }
        }

        public void bar() {
            for (int i = 0; i < n; i++) {
                synchronized (this) {
                    while (!flag) {
                        try {
                            this.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("bar");
                    flag = false;
                    this.notify();
                }
            }
        }
    }

    // 乐观锁版本
    static class FooBar2 implements FooBar {
        private final int n;
        private volatile boolean flag;

        public FooBar2(int n) {
            this.n = n;
        }

        public void foo() {
            for (int i = 0; i < n; i++) {
                while (flag) {
                    Thread.yield();
                }
                System.out.println("foo");
                flag = true;
            }
        }

        public void bar() {
            for (int i = 0; i < n; i++) {
                while (!flag) {
                    Thread.yield();
                }
                System.out.println("bar");
                flag = false;
            }
        }
    }
}
