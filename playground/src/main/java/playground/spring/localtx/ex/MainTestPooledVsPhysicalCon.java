package playground.spring.localtx.ex;

import antlr.collections.impl.IntRange;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import playground.spring.config.PersistenceJPAJavaConfig;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class MainTestPooledVsPhysicalCon {
    static AbstractApplicationContext context;
    static final int counter = 100;
    static CountDownLatch latchPooled = new CountDownLatch(counter);
    static CountDownLatch latchPhysical = new CountDownLatch(counter);

    public static void main(String[] args) {

        double pooledTime = testPooledConnections();
        double phyiscalTime = testPhysicalConnections();
        System.out.printf("Pooled [" + pooledTime + "]seconds, Physical [" + phyiscalTime + "]seconds");

    }

    private static double testPooledConnections() {
        context = new AnnotationConfigApplicationContext(PersistenceJPAJavaConfig.class);
        DataSource dataSource = context.getBean("dataSource", DataSource.class);
        long start = System.nanoTime();
        LongStream.range(0, latchPooled.getCount()).forEach(x -> {
            Connection conn = null;
            try {
                conn = dataSource.getConnection("PLAYGROUND", "PLAYGROUND");
                latchPooled.countDown();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        long end = 0;
        try {
            latchPooled.await();
            end = System.nanoTime();//End after await
            context.close();
            System.out.println("Total time to get connection :" + (end - start) / 1000000000.0 + " seconds");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return (end - start) / 1000000000.0;
    }

    private static double testPhysicalConnections() {

        long start = System.nanoTime();
        LongStream.range(0, latchPhysical.getCount()).forEach(x -> {
            Connection conn = null;
            try {
                conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orcl", "PLAYGROUND", "PLAYGROUND");
                latchPhysical.countDown();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        long end = 0;
        try {
            latchPhysical.await();
            end = System.nanoTime();//End after await
            System.out.println("Total time to get connection :" + (end - start) / 1000000000.0 + " seconds");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return (end - start) / 1000000000.0;
    }
}
