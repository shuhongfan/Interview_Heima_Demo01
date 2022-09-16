package day04.refresh;

import org.springframework.beans.factory.annotation.QualifierAnnotationAutowireCandidateResolver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanExpressionContext;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.expression.StandardBeanExpressionResolver;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.ResourcePropertySource;

import java.io.IOException;

// 如何获得和解析 @Value 内容
public class TestEnvironment {
    public static void main(String[] args) throws NoSuchFieldException, IOException {
        // 1) 获得 @Value 的值
        System.out.println("=======================> 仅获取 @Value 值");
        QualifierAnnotationAutowireCandidateResolver resolver = new QualifierAnnotationAutowireCandidateResolver();
        Object name = resolver.getSuggestedValue(new DependencyDescriptor(Bean1.class.getDeclaredField("name"), false));
        System.out.println(name);

        // 2) 解析 @Value 的值
        System.out.println("=======================> 获取 @Value 值, 并解析${}");
        Object javaHome = resolver.getSuggestedValue(new DependencyDescriptor(Bean1.class.getDeclaredField("javaHome"), false));
        System.out.println(javaHome);
        System.out.println(getEnvironment().resolvePlaceholders(javaHome.toString()));

        // 3) 解析 SpEL 表达式
        System.out.println("=======================> 获取 @Value 值, 并解析#{}");
        Object expression = resolver.getSuggestedValue(new DependencyDescriptor(Bean1.class.getDeclaredField("expression"), false));
        System.out.println(expression);
        String v1 = getEnvironment().resolvePlaceholders(expression.toString());
        System.out.println(v1);
        System.out.println(new StandardBeanExpressionResolver().evaluate(v1, new BeanExpressionContext(new DefaultListableBeanFactory(),null)));
    }

    private static Environment getEnvironment() throws IOException {
        StandardEnvironment env = new StandardEnvironment();
        env.getPropertySources().addLast(new ResourcePropertySource("jdbc", new ClassPathResource("jdbc.properties")));
        return env;
    }

    static class Bean1 {
        @Value("hello")
        private String name;

        @Value("${jdbc.username}")
        private String javaHome;

        @Value("#{'class version:' + '${java.class.version}'}")
        private String expression;
    }
}
