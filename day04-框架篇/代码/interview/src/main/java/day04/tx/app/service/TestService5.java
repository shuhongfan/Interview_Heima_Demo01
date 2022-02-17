package day04.tx.app.service;

import day04.tx.AppConfig;
import day04.tx.WebConfig;
import day04.tx.app.controller.AccountController;
import org.springframework.boot.context.properties.ConfigurationPropertiesBindingPostProcessor;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.support.GenericApplicationContext;

import java.io.FileNotFoundException;

public class TestService5 {
    public static void main(String[] args) throws FileNotFoundException {
        GenericApplicationContext parent = new GenericApplicationContext();
        AnnotationConfigUtils.registerAnnotationConfigProcessors(parent.getDefaultListableBeanFactory());
        ConfigurationPropertiesBindingPostProcessor.register(parent.getDefaultListableBeanFactory());
        parent.registerBean(AppConfig.class);
        parent.refresh();

        GenericApplicationContext child = new GenericApplicationContext();
        AnnotationConfigUtils.registerAnnotationConfigProcessors(child.getDefaultListableBeanFactory());
        child.setParent(parent);
        child.registerBean(WebConfig.class);
        child.refresh();

        AccountController bean = child.getBean(AccountController.class);
        bean.transfer(1, 2, 500);
    }
}
