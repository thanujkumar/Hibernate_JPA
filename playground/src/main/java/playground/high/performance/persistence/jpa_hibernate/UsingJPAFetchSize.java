package playground.high.performance.persistence.jpa_hibernate;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import playground.high.performance.config.HighPerformanceJPAJavaConfig;
import playground.high.performance.persistence.support.JPAFetchSizeHandlingSupport;
import playground.main.Logging;
import playground.model.Inventory;

import java.util.List;

//https://vladmihalcea.com/hibernate-performance-tuning-tips/
//hibernate.jdbc.fetch_size should be set to hibernate and when using pure jdbc should be set to oracle connection properties
public class UsingJPAFetchSize extends Logging {

    static AbstractApplicationContext context;

    //Transaction not in readOnly mode hence flushing is executed. Transaction.REQUIRED
    /*
      603900 nanoseconds spent acquiring 1 JDBC connections;
    0 nanoseconds spent releasing 0 JDBC connections;
    53628100 nanoseconds spent preparing 1 JDBC statements;
    31667200 nanoseconds spent executing 1 JDBC statements;
    0 nanoseconds spent executing 0 JDBC batches;
    0 nanoseconds spent performing 0 L2C puts;
    0 nanoseconds spent performing 0 L2C hits;
    0 nanoseconds spent performing 0 L2C misses;
    25931200 nanoseconds spent executing 1 flushes (flushing a total of 1112 entities and 0 collections);
    46800 nanoseconds spent executing 1 partial-flushes (flushing a total of 0 entities and 0 collections)
     */

    //readOnly=true and Transaction.REQUIRED - Here no flush
    /*
     538800 nanoseconds spent acquiring 1 JDBC connections;
    0 nanoseconds spent releasing 0 JDBC connections;
    54554400 nanoseconds spent preparing 1 JDBC statements;
    29971500 nanoseconds spent executing 1 JDBC statements;
    0 nanoseconds spent executing 0 JDBC batches;
    0 nanoseconds spent performing 0 L2C puts;
    0 nanoseconds spent performing 0 L2C hits;
    0 nanoseconds spent performing 0 L2C misses;
    0 nanoseconds spent executing 0 flushes (flushing a total of 0 entities and 0 collections);
    17800 nanoseconds spent executing 1 partial-flushes (flushing a total of 0 entities and 0 collections)
     */

    //readOnly=true and Transaction.SUPPORTS - Here no partial flush
    /*
     481800 nanoseconds spent acquiring 1 JDBC connections;
    0 nanoseconds spent releasing 0 JDBC connections;
    55909000 nanoseconds spent preparing 1 JDBC statements;
    31656400 nanoseconds spent executing 1 JDBC statements;
    0 nanoseconds spent executing 0 JDBC batches;
    0 nanoseconds spent performing 0 L2C puts;
    0 nanoseconds spent performing 0 L2C hits;
    0 nanoseconds spent performing 0 L2C misses;
    0 nanoseconds spent executing 0 flushes (flushing a total of 0 entities and 0 collections);
    0 nanoseconds spent executing 0 partial-flushes (flushing a total of 0 entities and 0 collections)
     */
    public static void main(String[] args) {
        context = new AnnotationConfigApplicationContext(HighPerformanceJPAJavaConfig.class);
        JPAFetchSizeHandlingSupport jpaFetchSize = context.getBean("jpaFetchSizeSupport", JPAFetchSizeHandlingSupport.class);
        for (int i = 0; i < 1 ;i++) {
            List<Inventory> inventoryList = jpaFetchSize.usingFetchSize_setAsHibernateProperty();
        }
        context.close();

    }
}
