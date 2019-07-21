package playground.spring.ucp;

import oracle.ucp.jdbc.PoolDataSource;
import oracle.ucp.jdbc.PoolDataSourceFactory;
import oracle.ucp.jdbc.PoolXADataSource;

import javax.sql.DataSource;
import javax.sql.XADataSource;
import java.sql.SQLException;

/**
 * This class provides to create both XA and non-XA connection.
 * <p>
 * For simplicity all properties are hardcoded as single datasource is used
 */
public class GlobalOracleConnectionPool {

    public static final String NARAYANA_ORA_URL = "jdbc:arjuna:oracle:thin:@localhost:1521/orcl";
    public static final String ORA_URL = "jdbc:oracle:thin:@localhost:1521/orcl";
    public static final String ORA_USER = "PLAYGROUND";
    public static final String ORA_PASSWORD = "PLAYGROUND";

    private static final String driverName = "oracle:";
    private static final String semicolon = ";";

    public static DataSource getPoolDatasource(String url) throws SQLException {
        PoolDataSource dataSource = PoolDataSourceFactory.getPoolDataSource();
        dataSource.setURL(url);
        dataSource.setConnectionFactoryClassName("oracle.jdbc.datasource.OracleDataSource");
        dataSource.setUser(ORA_PASSWORD);
        dataSource.setPassword(ORA_PASSWORD);
        dataSource.setLoginTimeout(10);
        dataSource.setMinPoolSize(0);
        dataSource.setMaxPoolSize(50);
        dataSource.setInitialPoolSize(0);
        dataSource.setInactiveConnectionTimeout(300);//5min
        dataSource.setConnectionPoolName("PLAYGROUND_POOL");
        return dataSource;
    }


    public static XADataSource getPoolXADatasource(String url) throws SQLException {
        PoolXADataSource dataSource = PoolDataSourceFactory.getPoolXADataSource();
        dataSource.setURL(url);
        dataSource.setConnectionFactoryClassName("oracle.jdbc.xa.client.OracleXADataSource");
        dataSource.setUser(ORA_PASSWORD);
        dataSource.setPassword(ORA_PASSWORD);
        dataSource.setLoginTimeout(10);
        dataSource.setMinPoolSize(0);
        dataSource.setMaxPoolSize(50);
        dataSource.setInitialPoolSize(0);
        dataSource.setInactiveConnectionTimeout(300);//5min
        dataSource.setConnectionPoolName("PLAYGROUND_XA_POOL");
        return dataSource;
    }

}
