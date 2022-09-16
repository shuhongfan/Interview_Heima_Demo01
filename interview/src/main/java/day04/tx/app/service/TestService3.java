package day04.tx.app.service;

import day02.LoggerUtils;
import day04.tx.AppConfig;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.context.properties.ConfigurationPropertiesBindingPostProcessor;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.io.FileNotFoundException;

public class TestService3 {
    public static void main(String[] args) throws FileNotFoundException {
        GenericApplicationContext context = new GenericApplicationContext();
        AnnotationConfigUtils.registerAnnotationConfigProcessors(context.getDefaultListableBeanFactory());
        ConfigurationPropertiesBindingPostProcessor.register(context.getDefaultListableBeanFactory());
        context.registerBean(MyAspect.class);
        context.registerBean(AppConfig.class);
        context.refresh();

        Service3 bean = context.getBean(Service3.class);
        bean.transfer(1, 2, 500);
    }

    @Aspect
    @Order(Ordered.LOWEST_PRECEDENCE - 1)
    static class MyAspect {
        @Around("execution(* transfer(..))")
        public Object around(ProceedingJoinPoint pjp) throws Throwable {
            LoggerUtils.get().debug("log:{}", pjp.getTarget());
            try {
                return pjp.proceed();
            } catch (Throwable e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
