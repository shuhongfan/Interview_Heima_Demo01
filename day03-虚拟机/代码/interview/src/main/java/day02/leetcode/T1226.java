package day02.leetcode;

import day02.LoggerUtils;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class T1226 {
    public static void main(String[] args) {
        DiningPhilosophers d = new DiningPhilosophers3();
        for (int i = 0; i < 5; i++) {
            int k = i;
            new Thread(() -> {
                try {
                    d.wantsToEat(k, () -> {
                        LoggerUtils.get("t").debug("pick left");
                    }, () -> {
                        LoggerUtils.get("t").debug("pick right");
                    }, () -> {
                        LoggerUtils.get("t").debug("eat");
                    }, () -> {
                        LoggerUtils.get("t").debug("put left");
                    }, () -> {
                        LoggerUtils.get("t").debug("put right");
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "t" + (i + 1)).start();
        }
    }

    interface DiningPhilosophers {
        public void wantsToEat(int philosopher,
                               Runnable pickLeftFork,
                               Runnable pickRightFork,
                               Runnable eat,
                               Runnable putLeftFork,
                               Runnable putRightFork) throws InterruptedException;
    }

    /*
        乐观锁解法: 关键在于加锁顺序, 正确顺序为
        * t0 要获取锁 1, 2
        * t1 要获取锁 2, 4
        * t2 要获取锁 4, 8
        * t3 要获取锁 8, 16
        * t4 要获取锁 1, 16
        错误情况是 t4 获取锁顺序为 16, 1, 思考以下场景
        t0 获取了 1
        t1 获取了 2
        t2 获取了 4
        t3 获取了 8
        t4 获取了 16
        此时它们均持有上一个线程需要的锁, 造成死锁
     */
    static class DiningPhilosophers3 implements DiningPhilosophers {
        AtomicInteger cas = new AtomicInteger();

        private final int[] locks = {1, 2, 4, 8, 16};

        public int getLeftLock(int n) {
            if (n == 4) {
                return locks[0];
            }
            return locks[n];
        }

        public int getRightLock(int n) {
            if (n == 4) {
                return locks[4];
            }
            return locks[n + 1];
        }
        /**
         * 说明1: 所有锁集中在一个变量上，用不同的位表示持锁状态(0 未持锁, 1 持锁)，方便 cas 原子操作
         * 说明2: 按位与检查锁是否未被占用, 若未占用, 用 cas 异或操作将锁位置为 1
         * 说明3: Leetcode 上的很多乐观锁实现均要加 Thread.sleep(1) 用其它方式反而超时, 具体性能未进行过比较
         * 说明4: 有些解法用 Semaphore 将线程限流为 4, 但实测发现并没有性能提升, 也无需用它解决死锁
         */
        public void wantsToEat(int philosopher,
                               Runnable pickLeftFork,
                               Runnable pickRightFork,
                               Runnable eat,
                               Runnable putLeftFork,
                               Runnable putRightFork) throws InterruptedException {
            int l = getLeftLock(philosopher);
            int r = getRightLock(philosopher);
            while (true) {
                int o = cas.get();
                if ((o & l) == 0 && cas.compareAndSet(o, o ^ l)) {
                    break;
                }
                Thread.sleep(1);
            }
            while (true) {
                int o = cas.get();
                if ((o & r) == 0 && cas.compareAndSet(o, o ^ r)) {
                    break;
                }
                Thread.sleep(1);
            }
            pickLeftFork.run();
            pickRightFork.run();
            eat.run();
            putRightFork.run();
            putLeftFork.run();
            while (true) {
                int o = cas.get();
                if (cas.compareAndSet(o, o ^ (r))) {
                    break;
                }
                Thread.sleep(1);
            }
            while (true) {
                int o = cas.get();
                if (cas.compareAndSet(o, o ^ (l))) {
                    break;
                }
                Thread.sleep(1);
            }
        }
    }

    /*
        悲观锁解法: 关键在于加锁顺序, 正确顺序为
        * t0 要获取锁 locks[0], locks[1]
        * t1 要获取锁 locks[1], locks[2]
        * t2 要获取锁 locks[2], locks[3]
        * t3 要获取锁 locks[3], locks[4]
        * t4 要获取锁 locks[0], locks[4]
        错误情况是 t4 获取锁顺序为 locks[4], locks[0], 思考以下场景
        t0 获取了 locks[0]
        t1 获取了 locks[1]
        t2 获取了 locks[2]
        t3 获取了 locks[3]
        t4 获取了 locks[4]
        此时它们均持有上一个线程需要的锁, 造成死锁
     */
    static class DiningPhilosophers2 implements DiningPhilosophers {
        Object[] locks = {
                new Object(),
                new Object(),
                new Object(),
                new Object(),
                new Object()
        };

        public Object getLeftLock(int n) {
            if (n == 4) {
                return locks[0];
            }
            return locks[n];
        }

        public Object getRightLock(int n) {
            if (n == 4) {
                return locks[4];
            }
            return locks[n + 1];
        }

        public void wantsToEat(int philosopher, Runnable pickLeftFork, Runnable pickRightFork, Runnable eat, Runnable putLeftFork, Runnable putRightFork) throws InterruptedException {
            synchronized (getLeftLock(philosopher)) {
                synchronized (getRightLock(philosopher)) {
                    pickLeftFork.run();
                    pickRightFork.run();
                    eat.run();
                    putRightFork.run();
                    putLeftFork.run();
                }
            }
        }
    }

    static class DiningPhilosophers1 implements DiningPhilosophers {
        Lock[] locks = {
                new ReentrantLock(),
                new ReentrantLock(),
                new ReentrantLock(),
                new ReentrantLock(),
                new ReentrantLock()
        };

        public Lock getLeftLock(int n) {
            return locks[n];
        }

        public Lock getRightLock(int n) {
            return locks[(n + 1) % 5];
        }

        /**
         * 用 tryLock 可以避免死锁, 所以不必考虑加锁顺序
         * 同样不加 tryLock timeout, Leetcode 上运行会超时
         */
        public void wantsToEat(int philosopher,
                               Runnable pickLeftFork,
                               Runnable pickRightFork,
                               Runnable eat,
                               Runnable putLeftFork,
                               Runnable putRightFork) throws InterruptedException {
            while (true) {
                boolean ls = getLeftLock(philosopher).tryLock(1, TimeUnit.MILLISECONDS);
                if (ls) {
                    try {
                        if (getRightLock(philosopher).tryLock(1, TimeUnit.MILLISECONDS)) {
                            pickLeftFork.run();
                            pickRightFork.run();
                            eat.run();
                            putRightFork.run();
                            putLeftFork.run();
                            getRightLock(philosopher).unlock();
                            break;
                        }
                    } finally {
                        getLeftLock(philosopher).unlock();
                    }
                }
            }
        }
    }
}
