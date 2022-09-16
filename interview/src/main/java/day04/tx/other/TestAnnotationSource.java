package day04.tx.other;

import org.springframework.transaction.annotation.AnnotationTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionAttribute;

public class TestAnnotationSource {
    public static void main(String[] args) throws NoSuchMethodException {
        AnnotationTransactionAttributeSource attributeSource = new AnnotationTransactionAttributeSource(false);
        TransactionAttribute attr = attributeSource.getTransactionAttribute(
                ServiceCImpl.class.getDeclaredMethod("transfer", int.class, int.class, int.class),
                ServiceCImpl.class
        );
        System.out.println(attr);
    }
}
