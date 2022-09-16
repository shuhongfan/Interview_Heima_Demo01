package day03.reference;

import day02.LoggerUtils;

import java.io.IOException;

public class TestFinalize {
    static class Dog {
        private String name;

        public Dog(String name) {
            this.name = name;
        }

        @Override
        protected void finalize() throws Throwable {
            LoggerUtils.get().debug("{}被干掉了?", this.name);
            int i = 1 / 0;
        }
    }

    public static void main(String[] args) throws IOException {
        new Dog("大傻");
        new Dog("二哈");
        new Dog("三笨");
        System.gc();
        System.in.read();
    }
    /*
    第一，从表面上我们能看出来 finalize 方法的调用次序并不能保证
    第二，日志中的 Finalizer 表示输出日志的线程名称，从这我们看出是这个叫做 Finalizer 的线程调用的 finalize 方法
    第三，你不能注释掉 `System.in.read()`，否则会发现（绝大概率）并不会有任何输出结果了，从这我们看出 finalize 中的代码并不能保证被执行
    第四，如果将 finalize 中的代码出现异常，会发现根本没有异常输出
    第五，还有个疑问，垃圾回收时就会立刻调用  finalize 方法吗？
     */
}