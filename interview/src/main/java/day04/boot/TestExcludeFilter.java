package day04.boot;

import day02.LoggerUtils;
import day04.boot.sub.Bean1;
import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;

import java.io.IOException;

public class TestExcludeFilter {

    public static void main(String[] args) {
        GenericApplicationContext context = new GenericApplicationContext();
        AnnotationConfigUtils.registerAnnotationConfigProcessors(context.getDefaultListableBeanFactory());
        context.registerBean(MyConfig.class);
        context.registerBean(MyFilter.class);
        context.refresh();
        for (String name : context.getBeanDefinitionNames()) {
            System.out.println(name);
        }
    }

    @Configuration
    @ComponentScan(basePackages = {"day04.boot.sub"},
            excludeFilters = { @ComponentScan.Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class)})
    static class MyConfig {

    }

    // 配合 @ComponentScan 使用, 对扫描到的元数据进行过滤, 返回 true 表示过滤掉, false 表示保留
    static class MyFilter extends TypeExcludeFilter {
        @Override
        public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
            String className = metadataReader.getClassMetadata().getClassName();
            LoggerUtils.get().debug("{}", className);
            if (className.equals(Bean1.class.getName())) {
                return true;
            }
            return false;
        }
    }
}
