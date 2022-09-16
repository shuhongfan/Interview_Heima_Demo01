package day04.tx.app.service;

import day04.tx.AppConfig;
import org.slf4j.MDC;
import org.springframework.boot.context.properties.ConfigurationPropertiesBindingPostProcessor;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.support.GenericApplicationContext;

import java.util.concurrent.CountDownLatch;

public class TestService7 {

    public static void main(String[] args) throws InterruptedException {
        GenericApplicationContext context = new GenericApplicationContext();
        AnnotationConfigUtils.registerAnnotationConfigProcessors(context.getDefaultListableBeanFactory());
        ConfigurationPropertiesBindingPostProcessor.register(context.getDefaultListableBeanFactory());
        context.registerBean(AppConfig.class);
        context.refresh();

        Service7 bean = context.getBean(Service7.class);
//        Object lock = new Object();

        CountDownLatch latch = new CountDownLatch(2);
        new MyThread(() -> {
//            synchronized (lock) {
                bean.transfer(1, 2, 1000);
//            }
            latch.countDown();
        }, "t1", "boldMagenta").start();

        new MyThread(() -> {
//            synchronized (lock) {
                bean.transfer(1, 2, 1000);
//            }
            latch.countDown();
        }, "t2", "boldBlue").start();

        latch.await();
        System.out.println(bean.findBalance(1));
    }

    static class MyThread extends Thread {
        private String color;

        public MyThread(Runnable target, String name, String color) {
            super(target, name);
            this.color = color;
        }

        @Override
        public void run() {
            MDC.put("thread", color);
            super.run();
            MDC.remove("thread");
        }
    }
}
