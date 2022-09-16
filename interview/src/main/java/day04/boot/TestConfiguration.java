package day04.boot;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;

public class TestConfiguration {
    public static void main(String[] args) {
        GenericApplicationContext context = new GenericApplicationContext();
        AnnotationConfigUtils.registerAnnotationConfigProcessors(context.getDefaultListableBeanFactory());
        context.registerBean("myConfig", MyConfig.class);
        context.refresh();
//        System.out.println(context.getBean(MyConfig.class).getClass());
    }

    @Configuration
    @MapperScan("aaa")
    // 注意点1: 配置类其实相当于一个工厂, 标注 @Bean 注解的方法相当于工厂方法
    static class MyConfig {
        // 注意点2: @Bean 不支持方法重载, 如果有多个重载方法, 仅有一个能入选为工厂方法
        // 注意点3: @Configuration 默认会为标注的类生成代理, 其目的是保证 @Bean 方法相互调用时, 仍然能保证其单例特性
        /*@Bean
        public Bean1 bean1() {
            System.out.println("bean1()");
            System.out.println(bean2());
            System.out.println(bean2());
            System.out.println(bean2());
            return new Bean1();
        }*/

        /*@Bean
        public Bean1 bean1(@Value("${java.class.version}") String a) {
            System.out.println("bean1(" + a + ")");
            return new Bean1();
        }

        @Bean
        public Bean1 bean1(@Value("${java.class.version}") String a, @Value("${JAVA_HOME}") String b) {
            System.out.println("bean1(" + a + ", " + b + ")");
            return new Bean1();
        }*/

        /*@Bean
        public Bean2 bean2() {
            System.out.println("bean2()");
            return new Bean2();
        }*/

        // 注意点4: @Configuration 中如果含有 BeanFactory 后处理器, 则实例工厂方法会导致 MyConfig 提前创建, 造成其依赖注入失败
        // 解决方法是该用静态工厂方法或直接为 @Bean 的方法参数依赖注入, 针对 MapperScanner 可以改用注解方式
        @Value("${java.class.version}")
        private String version;

        /*@Bean
        public static MapperScannerConfigurer configurer() {
            MapperScannerConfigurer scanner = new MapperScannerConfigurer();
            scanner.setBasePackage("aaa");
            return scanner;
        }*/

        @Bean
        public Bean3 bean3() {
            System.out.println("bean3() " + version);
            return new Bean3();
        }
    }

    static class Bean1 {

    }

    static class Bean2 {

    }

    static class Bean3 {

    }
}
