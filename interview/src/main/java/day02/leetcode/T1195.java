package day02.leetcode;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class T1195 {
    public static void main(String[] args) {
        FizzBuzz fb = new FizzBuzz3(30);
        new Thread(fb::fizz).start();
        new Thread(fb::buzz).start();
        new Thread(fb::fizzbuzz).start();
        new Thread(fb::number).start();

    }

    static interface FizzBuzz {
        public void fizz();

        public void buzz();

        public void fizzbuzz();

        public void number();
    }

    // 事先计算好每个 i 该打印什么, 用 1,2,3,4 区分, 用乐观锁检查条件
    static class FizzBuzz3 implements FizzBuzz {

        private final int n;
        private final int[] array;
        private AtomicInteger i = new AtomicInteger();

        public FizzBuzz3(int n) {
            this.n = n;
            this.array = new int[n];
            for (int i = 1; i <= n; i++) {
                if (i % 3 == 0 && i % 5 != 0) {
                    array[i - 1] = 1;
                } else if (i % 3 != 0 && i % 5 == 0) {
                    array[i - 1] = 2;
                } else if (i % 3 == 0) {
                    array[i - 1] = 3;
                } else {
                    array[i - 1] = 4;
                }
            }
        }

        @Override
        public void fizz() {
            out:
            while (i.get() < n) {
                while (true) {
                    int i = this.i.get();
                    if (i >= n) {
                        break out;
                    }
                    if (array[i] == 1) {
                        break;
                    }
                    Thread.yield();
                }
                System.out.println("fizz");
                i.incrementAndGet();
            }
        }

        @Override
        public void buzz() {
            out:
            while (i.get() < n) {
                while (true) {
                    int i = this.i.get();
                    if (i >= n) {
                        break out;
                    }
                    if (array[i] == 2) {
                        break;
                    }
                    Thread.yield();
                }
                System.out.println("buzz");
                i.incrementAndGet();
            }
        }

        @Override
        public void fizzbuzz() {
            out:
            while (i.get() < n) {
                while (true) {
                    int i = this.i.get();
                    if (i >= n) {
                        break out;
                    }
                    if (array[i] == 3) {
                        break;
                    }
                    Thread.yield();
                }
                System.out.println("fizzbuzz");
                i.incrementAndGet();
            }
        }

        @Override
        public void number() {
            out:
            while (i.get() < n) {
                while (true) {
                    int i = this.i.get();
                    if (i >= n) {
                        break out;
                    }
                    if (array[i] == 4) {
                        break;
                    }
                    Thread.yield();
                }
                System.out.println(i.get() + 1);
                i.incrementAndGet();
            }
        }
    }

    // 用多个条件变量精确控制唤醒, 性能较其他版本更佳
    static class FizzBuzz2 implements FizzBuzz {

        private final int n;
        private int i = 1;
        private final ReentrantLock lock = new ReentrantLock();
        private final Condition c1 = lock.newCondition();
        private final Condition c2 = lock.newCondition();
        private final Condition c3 = lock.newCondition();
        private final Condition c4 = lock.newCondition();

        public FizzBuzz2(int n) {
            this.n = n;
        }

        @Override
        public void fizz() {
            while (i <= n) {
                lock.lock();
                try {
                    if (i % 3 == 0 && i % 5 != 0) {
                        System.out.println("fizz");
                        i++;
                        if (i % 3 != 0 && i % 5 == 0) {
                            c2.signal();
                        } else if (i % 3 == 0 && i % 5 == 0) {
                            c3.signal();
                        } else {
                            c4.signal();
                        }
                        if (i == n) {
                            c2.signal();
                            c3.signal();
                            c4.signal();
                        }
                    } else {
                        try {
                            c1.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                } finally {
                    lock.unlock();
                }
            }
        }

        @Override
        public void buzz() {
            while (i <= n) {
                lock.lock();
                try {
                    if (i % 3 != 0 && i % 5 == 0) {
                        System.out.println("buzz");
                        i++;
                        if (i % 3 == 0 && i % 5 == 0) {
                            c3.signal();
                        } else if (i % 3 != 0 && i % 5 != 0) {
                            c4.signal();
                        } else {
                            c1.signal();
                        }
                        if (i == n) {
                            c1.signal();
                            c3.signal();
                            c4.signal();
                        }
                    } else {
                        try {
                            c2.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } finally {
                    lock.unlock();
                }
            }
        }

        @Override
        public void fizzbuzz() {
            while (i <= n) {
                lock.lock();
                try {
                    if (i % 3 == 0 && i % 5 == 0) {
                        System.out.println("fizzbuzz");
                        i++;
                        if (i % 3 != 0 && i % 5 != 0) {
                            c4.signal();
                        } else if (i % 3 == 0 && i % 5 != 0) {
                            c1.signal();
                        } else {
                            c2.signal();
                        }
                        if (i == n) {
                            c1.signal();
                            c2.signal();
                            c4.signal();
                        }
                    } else {
                        try {
                            c3.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                } finally {
                    lock.unlock();
                }
            }
        }

        @Override
        public void number() {
            while (i <= n) {
                lock.lock();
                try {
                    if (i % 3 != 0 && i % 5 != 0) {
                        System.out.println(i);
                        i++;
                        if (i % 3 == 0 && i % 5 != 0) {
                            c1.signal();
                        } else if (i % 3 != 0 && i % 5 == 0) {
                            c2.signal();
                        } else {
                            c3.signal();
                        }
                        if (i == n) {
                            c1.signal();
                            c2.signal();
                            c3.signal();
                        }
                    } else {
                        try {
                            c4.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                } finally {
                    lock.unlock();
                }
            }
        }
    }

    // 普通 synchronized 版本实现
    static class FizzBuzz1 implements FizzBuzz {
        private final int n;
        private int i = 1;

        public FizzBuzz1(int n) {
            this.n = n;
        }

        public void fizz() {
            while (i <= n) {
                synchronized (this) {
                    if (i % 3 == 0 && i % 5 != 0) {
                        System.out.println("fizz");
                        i++;
                        this.notifyAll();
                    } else {
                        try {
                            this.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        public void buzz() {
            while (i <= n) {
                synchronized (this) {
                    if (i % 3 != 0 && i % 5 == 0) {
                        System.out.println("buzz");
                        i++;
                        this.notifyAll();
                    } else {
                        try {
                            this.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        public void fizzbuzz() {
            while (i <= n) {
                synchronized (this) {
                    if (i % 3 == 0 && i % 5 == 0) {
                        System.out.println("fizzbuzz");
                        i++;
                        this.notifyAll();
                    } else {
                        try {
                            this.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        public void number() {
            while (i <= n) {
                synchronized (this) {
                    if (i % 3 != 0 && i % 5 != 0) {
                        System.out.println(i);
                        i++;
                        this.notifyAll();
                    } else {
                        try {
                            this.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
