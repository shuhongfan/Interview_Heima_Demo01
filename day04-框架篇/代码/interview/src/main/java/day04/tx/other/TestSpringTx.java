package day04.tx.other;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionStatus;

public class TestSpringTx {

    public static void main(String[] args) {
        GenericApplicationContext context = new GenericApplicationContext();
        context.registerBean("myConfig", MyConfig.class);
        context.registerBean("bean1", Bean1.class);
        context.registerBean("bean2", Bean2.class);
        context.registerBean("bean3", Bean3.class);
        context.registerBean("aspect1", Aspect1.class);
        context.registerBean(ConfigurationClassPostProcessor.class);
        context.refresh();

        context.getBean(Bean1.class).foo();
        System.out.println(context.getBean(Bean1.class).getClass());
    }

    @EnableTransactionManagement
    @EnableAspectJAutoProxy
    static class MyConfig {
        @Bean
        public PlatformTransactionManager transactionManager() {
            return new PlatformTransactionManager() {
                @Override
                public TransactionStatus getTransaction(TransactionDefinition definition) throws TransactionException {
                    System.out.println(definition);
                    return new DefaultTransactionStatus(definition, true, true, true, true, null);
                }

                @Override
                public void commit(TransactionStatus status) throws TransactionException {
                    System.out.println("commit");
                }

                @Override
                public void rollback(TransactionStatus status) throws TransactionException {
                    System.out.println("rollback");
                }
            };
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    static class Bean1 {
        public void foo() {
            System.out.println("foo");
        }
    }

    static class Bean2 {
        @Transactional
        public void foo() {

        }
    }

    static class Bean3 {
        public void foo() {

        }
    }

    @Aspect
//    @Order(1)
    static class Aspect1 {
        @Around("execution(* foo())")
        public void around(ProceedingJoinPoint pjp) throws Throwable {
            System.out.println("before...");
            pjp.proceed();
            System.out.println("after...");
        }
    }
}
