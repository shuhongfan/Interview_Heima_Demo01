package day04.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.support.GenericApplicationContext;

// 测试如果对同一属性进行的 @Autowired 注入、AUTOWIRE_BY_NAME、精确指定注入名称, 优先级是怎样的
public class TestInjection {
    public static void main(String[] args) {
        GenericApplicationContext context = new GenericApplicationContext();
        AnnotationConfigUtils.registerAnnotationConfigProcessors(context.getDefaultListableBeanFactory());
        context.registerBean("bean1", Bean1.class, bd -> {
            // 优先级最高的：精确指定注入 bean 的名称 <property name="bean3" ref="bean2"/>
            bd.getPropertyValues().add("bean3", new RuntimeBeanReference("bean2"));
            // 优先级次之的：通过 AUTOWIRE_BY_NAME 匹配
            ((RootBeanDefinition) bd).setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_NAME);
        });
        context.registerBean("bean2", Bean2.class);
        context.registerBean("bean3", Bean3.class);
        context.registerBean("bean4", Bean4.class);

        context.refresh();
    }

    static class Bean1 {
        MyInterface bean;

        // 优先级最低的：@Autowired 匹配
        @Autowired @Qualifier("bean4")
        public void setBean3(MyInterface bean) {
            System.out.println(bean);
            this.bean = bean;
        }
    }

    interface MyInterface {
    }

    static class Bean2 implements MyInterface {
    }

    static class Bean3 implements MyInterface {
    }

    static class Bean4 implements MyInterface {
    }
}
