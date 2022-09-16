package day04.boot;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.*;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.type.AnnotationMetadata;

public class TestImport {
    public static void main(String[] args) {
        GenericApplicationContext context = new GenericApplicationContext();
        AnnotationConfigUtils.registerAnnotationConfigProcessors(context.getDefaultListableBeanFactory());
        context.registerBean(MyConfig.class);
        context.refresh();

        for (String name : context.getBeanDefinitionNames()) {
            System.out.println(name);
        }

    }

    @Configuration
//    @Import(Bean1.class) // 1. 引入单个 bean
//    @Import(OtherConfig.class) // 2. 引入一个配置类
    @Import(MySelector.class) // 3. 通过 Selector 引入多个类
//    @Import(MyRegistrar.class) // 4. 通过 beanDefinition 注册器
    static class MyConfig {

    }

    static class MySelector implements DeferredImportSelector {
        @Override
        public String[] selectImports(AnnotationMetadata importingClassMetadata) {
            return new String[]{Bean3.class.getName(), Bean4.class.getName()};
        }
    }

    static class MyRegistrar implements ImportBeanDefinitionRegistrar {
        @Override
        public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
            registry.registerBeanDefinition("bean5", BeanDefinitionBuilder.genericBeanDefinition(Bean5.class).getBeanDefinition());
        }
    }

    static class Bean5 {

    }

    static class Bean3 {

    }

    static class Bean4 {

    }

    static class Bean1 {

    }

    @Configuration
    static class OtherConfig {
        @Bean
        public Bean2 bean2() {
            return new Bean2();
        }
    }

    static class Bean2 {

    }


}
