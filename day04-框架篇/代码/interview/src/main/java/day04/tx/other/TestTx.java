package day04.tx.other;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.AnnotationTransactionAttributeSource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.BeanFactoryTransactionAttributeSourceAdvisor;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.lang.reflect.Method;

public class TestTx {
    public static void main(String[] args) {
        AnnotationTransactionAttributeSource attributeSource = new AnnotationTransactionAttributeSource();

        TransactionInterceptor advice = new TransactionInterceptor();
        advice.setTransactionManager(new PlatformTransactionManager() {
            @Override
            public TransactionStatus getTransaction(TransactionDefinition definition) throws TransactionException {
                return null;
            }

            @Override
            public void commit(TransactionStatus status) throws TransactionException {
                System.out.println("commit");
            }

            @Override
            public void rollback(TransactionStatus status) throws TransactionException {
                System.out.println("rollback");
            }
        });

        BeanFactoryTransactionAttributeSourceAdvisor advisor = new BeanFactoryTransactionAttributeSourceAdvisor();
        advisor.setTransactionAttributeSource(attributeSource);
        advisor.setAdvice(advice);

        for (Method method : Bean1.class.getDeclaredMethods()) {
            System.out.println(attributeSource.getTransactionAttribute(method, Bean1.class));
            System.out.println(advisor.getPointcut().getMethodMatcher().matches(method, Bean1.class));
        }

        for (Method method : Bean2.class.getDeclaredMethods()) {
            System.out.println(attributeSource.getTransactionAttribute(method, Bean2.class));
            System.out.println(advisor.getPointcut().getMethodMatcher().matches(method, Bean2.class));
        }

        for (Method method : Bean3.class.getDeclaredMethods()) {
            System.out.println(attributeSource.getTransactionAttribute(method, Bean3.class));
            System.out.println(advisor.getPointcut().getMethodMatcher().matches(method, Bean3.class));
        }
    }

    @Transactional
    static class Bean1 {
        public void foo() {

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
}
