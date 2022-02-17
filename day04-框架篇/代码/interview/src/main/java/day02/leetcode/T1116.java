package day02.leetcode;

public class T1116 {
    public static void main(String[] args) {
        ZeroEvenOdd zeo = new ZeroEvenOdd1(5);
        new Thread(zeo::zero, "zero").start();
        new Thread(zeo::even, "even").start();
        new Thread(zeo::odd, "odd").start();
    }

    static interface ZeroEvenOdd {
        public void zero();

        public void even();

        public void odd();
    }

    static class ZeroEvenOdd1 implements ZeroEvenOdd {
        private final int n;
        private int flag = 0;

        public ZeroEvenOdd1(int n) {
            this.n = n;
        }

        @Override
        public void zero() {
            for (int i = 0; i < n; i++) {
                synchronized (this) {
                    while (flag != 0) {
                        try {
                            this.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.print(0);

                    if ((i & 1) == 0) {
                        flag = 1;
                    } else {
                        flag = 2;
                    }
                    this.notifyAll();
                }
            }
        }

        @Override
        public void even() {
            for (int i = 2; i <= n; i += 2) {
                synchronized (this) {
                    while (flag != 2) {
                        try {
                            this.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.print(i);
                    flag = 0;
                    this.notifyAll();

                }
            }
        }

        @Override
        public void odd() {
            for (int i = 1; i <= n; i += 2) {
                synchronized (this) {
                    while (flag != 1) {
                        try {
                            this.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.print(i);
                    flag = 0;
                    this.notifyAll();

                }
            }
        }
    }

}
