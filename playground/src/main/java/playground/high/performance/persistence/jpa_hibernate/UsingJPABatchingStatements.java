package playground.high.performance.persistence.jpa_hibernate;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import playground.high.performance.config.HighPerformanceJPAJavaConfig;
import playground.high.performance.persistence.support.JPABatchHandlingSupport;
import playground.main.Logging;
import playground.service.RegionService;

//https://www.baeldung.com/jpa-hibernate-batch-insert-update
public class UsingJPABatchingStatements extends Logging {
    static AbstractApplicationContext context;

    /*
     435100 nanoseconds spent acquiring 1 JDBC connections;
    0 nanoseconds spent releasing 0 JDBC connections;
    56776700 nanoseconds spent preparing 11 JDBC statements;
    63424400 nanoseconds spent executing 11 JDBC statements;
    0 nanoseconds spent executing 0 JDBC batches;
    0 nanoseconds spent performing 0 L2C puts;
    0 nanoseconds spent performing 0 L2C hits;
    0 nanoseconds spent performing 0 L2C misses;
    55903300 nanoseconds spent executing 1 flushes (flushing a total of 10 entities and 0 collections);
    0 nanoseconds spent executing 0 partial-flushes (flushing a total of 0 entities and 0 collections)
     */

    //With Batch size set to 10
    /*
    443300 nanoseconds spent acquiring 1 JDBC connections;
    0 nanoseconds spent releasing 0 JDBC connections;
    56080400 nanoseconds spent preparing 2 JDBC statements;
    38800200 nanoseconds spent executing 1 JDBC statements;
    8494000 nanoseconds spent executing 1 JDBC batches;
    0 nanoseconds spent performing 0 L2C puts;
    0 nanoseconds spent performing 0 L2C hits;
    0 nanoseconds spent performing 0 L2C misses;
    29023300 nanoseconds spent executing 1 flushes (flushing a total of 10 entities and 0 collections);
    0 nanoseconds spent executing 0 partial-flushes (flushing a total of 0 entities and 0 collections)
     */

    /**
     * Ensure to disable batch size in persistence.xml
     * hibernate.jdbc.batch_size
     * @param args
     */
    public static void main(String[] args) {
        context = new AnnotationConfigApplicationContext(HighPerformanceJPAJavaConfig.class);
        JPABatchHandlingSupport jpaBatch = context.getBean("jpaBatchSupport", JPABatchHandlingSupport.class);
        jpaBatch.whenBatchNotConfigured_InsertSeparately();
        context.close();
    }
}
