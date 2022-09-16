package day04.tx.app.service;

import day04.tx.AppConfig;
import org.springframework.boot.context.properties.ConfigurationPropertiesBindingPostProcessor;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.support.GenericApplicationContext;

import java.io.FileNotFoundException;

public class TestService4 {
    public static void main(String[] args) throws FileNotFoundException {
        GenericApplicationContext context = new GenericApplicationContext();
        AnnotationConfigUtils.registerAnnotationConfigProcessors(context.getDefaultListableBeanFactory());
        ConfigurationPropertiesBindingPostProcessor.register(context.getDefaultListableBeanFactory());
        context.registerBean(AppConfig.class);
        context.refresh();

        Service4 bean = context.getBean(Service4.class);
        bean.transfer(1, 2, 500);
    }
}
