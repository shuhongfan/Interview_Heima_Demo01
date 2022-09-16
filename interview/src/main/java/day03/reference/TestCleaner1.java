package day03.reference;

import day02.LoggerUtils;

import java.io.IOException;
import java.lang.ref.Cleaner;

// 前面讲的弱、虚引用配合引用队列，目的都是为了找到哪些 java 对象被回收，从而进行对它们关联的资源进行进一步清理
// 为了简化 api 难度，从 java 9 开始引入了 Cleaner 对象
public class TestCleaner1 {
    public static void main(String[] args) throws IOException {
        Cleaner cleaner = Cleaner.create();

        cleaner.register(new MyResource(), ()-> LoggerUtils.get().debug("clean 1"));
        cleaner.register(new MyResource(), ()-> LoggerUtils.get().debug("clean 2"));
        cleaner.register(new MyResource(), ()-> LoggerUtils.get().debug("clean 3"));
        MyResource obj = new MyResource();
        cleaner.register(obj, ()-> LoggerUtils.get().debug("clean 4"));
        cleaner.register(new MyResource(), ()-> LoggerUtils.get().debug("clean 5"));
        cleaner.register(new MyResource(), ()-> LoggerUtils.get().debug("clean 6"));

        System.gc();
        System.in.read();
    }

    static class MyResource {

    }
}
