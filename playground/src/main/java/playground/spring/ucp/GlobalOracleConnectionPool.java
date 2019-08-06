package playground.spring.ucp;

import oracle.jdbc.driver.OracleConnection;
import oracle.ucp.jdbc.PoolDataSource;
import oracle.ucp.jdbc.PoolDataSourceFactory;
import oracle.ucp.jdbc.PoolXADataSource;

import javax.sql.DataSource;
import javax.sql.XADataSource;
import java.sql.SQLException;
import java.util.Properties;

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
        dataSource.setURL(url2);
        dataSource.setConnectionFactoryClassName("oracle.jdbc.pool.OracleDataSource");
        dataSource.setUser(ORA_USER);
        dataSource.setPassword(ORA_PASSWORD);
        dataSource.setLoginTimeout(10);
        dataSource.setMinPoolSize(0);
        dataSource.setMaxPoolSize(50);
        dataSource.setInitialPoolSize(0);
        dataSource.setInactiveConnectionTimeout(300);//5min
        dataSource.setConnectionPoolName("PLAYGROUND_POOL");
        Properties conProp = new Properties();
        conProp.setProperty(OracleConnection.CONNECTION_PROPERTY_THIN_READ_TIMEOUT, "10000");
        conProp.setProperty(OracleConnection.CONNECTION_PROPERTY_DEFAULT_ROW_PREFETCH, "20");
        conProp.put("v$session.program", "TestAppOnline");
        dataSource.setMaxStatements(10);
        dataSource.setConnectionProperties(conProp);
        return dataSource;
    }

  static String url2 = "jdbc:oracle:thin:@(DESCRIPTION=(CONNECT_TIMEOUT=10000)(RETRY_COUNT=3)(TRANSPORT_CONNECT_TIMEOUT=1)(ADDRESS_LIST=(LOAD_BALANCE=ON)(FAILOVER=ON)(ADDRESS=(PROTOCOL=TCP)(HOST=localhost)(PORT=1521)))(CONNECT_DATA=(PROGRAM=TestAppOnline)(SERVER=DEDICATED)(SERVICE_NAME=orcl)))";

    public static XADataSource getPoolXADatasource(String url) throws SQLException {
        PoolXADataSource dataSource = PoolDataSourceFactory.getPoolXADataSource();
        dataSource.setURL(url2);
        dataSource.setConnectionFactoryClassName("oracle.jdbc.xa.client.OracleXADataSource");
        dataSource.setUser(ORA_USER);
        dataSource.setPassword(ORA_PASSWORD);
        dataSource.setLoginTimeout(10);
        dataSource.setMinPoolSize(0);
        dataSource.setMaxPoolSize(50);
        dataSource.setInitialPoolSize(0);
        dataSource.setInactiveConnectionTimeout(300);//5min
        dataSource.setConnectionPoolName("PLAYGROUND_XA_POOL");
        dataSource.setMaxStatements(10);
        Properties conProp = new Properties();
        conProp.setProperty(OracleConnection.CONNECTION_PROPERTY_THIN_READ_TIMEOUT, "10000");
        conProp.setProperty(OracleConnection.CONNECTION_PROPERTY_DEFAULT_ROW_PREFETCH, "20");
        conProp.put("v$session.program", "TestAppOnline");
        dataSource.setConnectionProperties(conProp);
        return dataSource;
    }

}
