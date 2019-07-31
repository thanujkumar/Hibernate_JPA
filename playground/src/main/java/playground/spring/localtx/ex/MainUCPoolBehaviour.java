package playground.spring.localtx.ex;

import oracle.ucp.UniversalConnectionPool;
import oracle.ucp.admin.UniversalConnectionPoolManagerImpl;
import oracle.ucp.jdbc.PoolDataSource;
import oracle.ucp.jdbc.PoolDataSourceFactory;
import playground.main.Logging;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;
import java.util.stream.LongStream;

public class MainUCPoolBehaviour extends Logging {
    public static final String ORA_URL = "jdbc:oracle:thin:@localhost:1521/orcl";
    public static final String ORA_USER = "PLAYGROUND";
    public static final String ORA_PASSWORD = "PLAYGROUND";

    public static DataSource getPoolDatasource(String url) throws SQLException {
        PoolDataSource dataSource = PoolDataSourceFactory.getPoolDataSource();
        dataSource.setURL(url);
        dataSource.setConnectionFactoryClassName("oracle.jdbc.pool.OracleDataSource");
        dataSource.setUser(ORA_USER);
        dataSource.setPassword(ORA_PASSWORD);
        dataSource.setLoginTimeout(10);
        dataSource.setMinPoolSize(2);
        dataSource.setMaxPoolSize(50);
        dataSource.setInitialPoolSize(0);
        dataSource.setInactiveConnectionTimeout(30);//5min
        dataSource.setConnectionPoolName("PLAYGROUND_POOL");
        return dataSource;
    }

    public static void destroy() throws Exception {
        String[] names = UniversalConnectionPoolManagerImpl.getUniversalConnectionPoolManager().getConnectionPoolNames();
        for (String name : names) {
            UniversalConnectionPool ds = UniversalConnectionPoolManagerImpl.getUniversalConnectionPoolManager().getConnectionPool(name);
            System.out.println("----------------------------------------------------------");
            System.out.println("PoolName:" + name);
            System.out.println(ds.getStatistics());
            System.out.println("----------------------------------------------------------");
            UniversalConnectionPoolManagerImpl.getUniversalConnectionPoolManager().destroyConnectionPool(name);
        }
    }

    static final int counter = 100;
    static CountDownLatch latchPooled = new CountDownLatch(counter);

    /**
     * Test UCP and verify count of connection created, connection closed and reused
     *
     * @param args
     */
    public static void main(String[] args) throws Exception {
        DataSource dataSource = getPoolDatasource(ORA_URL);
        long start = System.nanoTime();
        LongStream.range(0, latchPooled.getCount()).parallel().forEach(x -> {
            Connection conn = null;
            try {
                conn = dataSource.getConnection("PLAYGROUND", "PLAYGROUND");
                ResultSet rs = conn.createStatement().executeQuery("select 1 from dual");
                while (rs.next()) {
                    System.out.println(x +":"+rs.getString(1));
                }
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
            Thread.sleep(60000);//1 min
            destroy();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
