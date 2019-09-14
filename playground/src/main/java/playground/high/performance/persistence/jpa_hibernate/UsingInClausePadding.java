package playground.high.performance.persistence.jpa_hibernate;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import playground.high.performance.config.HighPerformanceJPAJavaConfig;
import playground.high.performance.persistence.support.JPAInClauseHandlingSupport;
import playground.main.Logging;
import playground.main.Logging2;
import playground.model.Order;

import java.util.List;

//https://vladmihalcea.com/improve-statement-caching-efficiency-in-clause-parameter-padding/
//https://vladmihalcea.com/hibernate-performance-tuning-tips/
//hibernate.query.in_clause_parameter_padding=true
public class UsingInClausePadding  extends Logging2 {
    static AbstractApplicationContext context;

    public static void main(String[] args) {
        context = new AnnotationConfigApplicationContext(HighPerformanceJPAJavaConfig.class);
        JPAInClauseHandlingSupport jpaInClause = context.getBean("jpaInClauseSupport", JPAInClauseHandlingSupport.class);
        List<Order> list = jpaInClause.usingInClauseOptimization(1L,2L,3L);
        System.out.println(list.size());

        list = jpaInClause.usingInClauseOptimization(1L,2L,3L, 4L, 5L, 6L);
        System.out.println(list.size());

        list = jpaInClause.usingInClauseOptimization(1L,2L,3L, 4L, 5L, 6L, 7L, 8L);
        System.out.println(list.size());

        list = jpaInClause.usingInClauseOptimization(1L,2L,3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L);
        System.out.println(list.size());
        context.close();
    }
}
