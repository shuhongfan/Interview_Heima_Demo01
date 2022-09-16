package day02;

import static day02.LoggerUtils.*;

/**
 * 结论
 * 0) 线程会被封装为 ObjectWaiter 对象, 根据状态不同, 在以下几个队列中移动
 * 1) WaitSet 是一个双向链表, 存储调用 wait 方法的 ObjectWaiter 对象, 先进先出, 默认出队时 EntryList 为空则放入 EntryList, 否则放入 _cxq 头
 * 2) _cxq 是一个栈, 存放竞争锁失败的, 或者是用 notify 唤醒时 EntryList 不为空的 ObjectWaiter 对象, 后进先出
 * 3) _cxq 里面的对象并不会永久挂起, 时不时会唤醒它们争抢锁
 * 4) _cxq 以外的线程争抢锁不需要考虑顺序
 * 5) 默认锁释放时从 EntryList 队列头取 ObjectWaiter 对象, 如果 EntryList 为空, 则取 _cxq 头
 */
public class TestOrder {
    final static Object LOCK = new Object();

    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (LOCK) {
                logger1.debug("waiting...");
                waitOnLock(LOCK);
                logger1.debug("end waiting...");
            }
        }, "a").start();

        sleep(200);

        synchronized (LOCK) {
            new Thread(() -> {
                logger2.debug("blocking...");
                synchronized (LOCK) {
                    logger2.debug("acquired...");
                }
            }, "b").start();
//            sleep(200);
            new Thread(() -> {
                logger3.debug("blocking...");
                synchronized (LOCK) {
                    logger3.debug("acquired...");
                }
            }, "c").start();
//            sleep(200);
            new Thread(() -> {
                logger4.debug("blocking...");
                synchronized (LOCK) {
                    logger4.debug("acquired...");
                }
            }, "d").start();
            new Thread(() -> {
                logger5.debug("blocking...");
                synchronized (LOCK) {
                    logger5.debug("acquired...");
                }
            }, "e").start();
            new Thread(() -> {
                logger6.debug("blocking...");
                synchronized (LOCK) {
                    logger6.debug("acquired...");
                }
            }, "f").start();
            sleep(1);
            main.debug("releasing...");
        }
    }

    private static void notifyAndSync() {
        new Thread(() -> {
            synchronized (LOCK) {
                logger2.debug("waiting...");
                waitOnLock(LOCK);
                logger2.debug("end waiting...");
            }
        }, "b").start();

        sleep(400);

        new Thread(() -> {
            synchronized (LOCK) {
                logger3.debug("waiting...");
                waitOnLock(LOCK);
                logger3.debug("end waiting...");
            }
        }, "c").start();

        sleep(200);

        new Thread(() -> {
            synchronized (LOCK) {
                logger4.debug("waiting...");
                waitOnLock(LOCK);
                logger4.debug("end waiting...");
            }
        }, "d").start();

        sleep(200);

        synchronized (LOCK) {
            new Thread(() -> {
                logger5.debug("blocking...");
                synchronized (LOCK) {
                    logger5.debug("acquired...");
                }
            }, "e").start();
            sleep(200);
            new Thread(() -> {
                logger6.debug("blocking...");
                synchronized (LOCK) {
                    logger6.debug("acquired...");
                }
            }, "f").start();
            sleep(200);
            main.debug("notify...");
            notifyOnLock(LOCK);
            sleep(200);
            main.debug("notify...");
            notifyOnLock(LOCK);
            sleep(200);
            main.debug("notify...");
            notifyOnLock(LOCK);
            sleep(200);
            main.debug("notify...");
            notifyOnLock(LOCK);
        }
    }

    private static void notifyOneByOne() {
        synchronized (LOCK) {
            main.debug("notify...");
            notifyOnLock(LOCK);
        }
        sleep(200);
        synchronized (LOCK) {
            main.debug("notify...");
            notifyOnLock(LOCK);
        }
        sleep(200);
        synchronized (LOCK) {
            main.debug("notify...");
            notifyOnLock(LOCK);
        }
        sleep(200);
        synchronized (LOCK) {
            main.debug("notify...");
            notifyOnLock(LOCK);
        }
    }

    private static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void waitOnLock(Object LOCK) {
        try {
            LOCK.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void notifyOnLock(Object LOCK) {
        LOCK.notify();
    }

    private static void notifyAllOnLock(Object LOCK) {
        LOCK.notifyAll();
    }
}
