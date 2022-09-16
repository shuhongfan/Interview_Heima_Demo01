package day04.bean;

import day02.LoggerUtils;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.RequestScope;
import org.springframework.web.context.request.ServletWebRequest;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class TestScope {
    public static void main(String[] args) {
        testRequestScope();
    }

    // 单例 bean 从 refresh 被创建, 到 close 被销毁, BeanFactory 会记录哪些 bean 要调用销毁方法
    private static void testSingletonScope() {
        GenericApplicationContext context = new GenericApplicationContext();
        context.registerBean("bean1", Bean1.class);
        context.registerBean(CommonAnnotationBeanPostProcessor.class);
        context.refresh(); // getBean
        context.close();
    }

    // 多例 bean 从首次 getBean 被创建, 到调用 BeanFactory 的 destroyBean 被销毁
    private static void testPrototypeScope() {
        GenericApplicationContext context = new GenericApplicationContext();
        context.registerBean("bean1", Bean1.class, bd -> bd.setScope("prototype"));
        context.registerBean(CommonAnnotationBeanPostProcessor.class);
        context.refresh();

        Bean1 bean = context.getBean(Bean1.class);
        // 没谁记录该 bean 要调用销毁方法, 需要我们自行调用
        context.getDefaultListableBeanFactory().destroyBean(bean);

        context.close();
    }

    // request bean 从首次 getBean 被创建, 到 request 结束前被销毁
    private static void testRequestScope() {
        GenericApplicationContext context = new GenericApplicationContext();
        context.getDefaultListableBeanFactory().registerScope("request", new RequestScope());
        context.registerBean("bean1", Bean1.class, bd -> bd.setScope("request"));
        context.registerBean(CommonAnnotationBeanPostProcessor.class);
        context.refresh();

        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                MockHttpServletRequest request = new MockHttpServletRequest();
                // 每个 webRequest 对象会记录哪些 bean 要调用销毁方法
                ServletWebRequest webRequest = new ServletWebRequest(request);
                RequestContextHolder.setRequestAttributes(webRequest);

                Bean1 bean = context.getBean(Bean1.class);
                LoggerUtils.get().debug("{}", bean);
                LoggerUtils.get().debug("{}", request.getAttribute("bean1"));

                // request 请求结束前调用这些销毁方法
                webRequest.requestCompleted();
            }).start();
        }

    }

    static class Bean1 {
        @PostConstruct
        public void init() {
            LoggerUtils.get().debug("{} - init", this);
        }

        @PreDestroy
        public void destroy() {
            LoggerUtils.get().debug("{} - destroy", this);
        }
    }
}
