package playground.spring.ucp;

import oracle.jdbc.OracleConnection;
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

    private static Properties conProp = new Properties();

    static {
        //https://vladmihalcea.com/hibernate-performance-tuning-tips/
        conProp.setProperty(OracleConnection.CONNECTION_PROPERTY_THIN_READ_TIMEOUT, "10000");
        //Oracle JDBC driver specification recommends limiting the fetch size to at most 100 records.
        conProp.setProperty(OracleConnection.CONNECTION_PROPERTY_DEFAULT_ROW_PREFETCH, "100");
        //By default implicit statement caching is enabled
        conProp.setProperty(OracleConnection.CONNECTION_PROPERTY_IMPLICIT_STATEMENT_CACHE_SIZE, "20");
        //Below property to support - https://vladmihalcea.com/hibernate-performance-tuning-tips/ connection management
        //https://vladmihalcea.com/why-you-should-always-use-hibernate-connection-provider_disables_autocommit-for-resource-local-jpa-transactions/
        conProp.setProperty(OracleConnection.CONNECTION_PROPERTY_AUTOCOMMIT, "false");
        conProp.setProperty("v$session.program", "HIBERNATE_JPA2");
    }

    public static DataSource getPoolDatasource(String url) throws SQLException {
        PoolDataSource dataSource = PoolDataSourceFactory.getPoolDataSource();
        dataSource.setURL(url);
        dataSource.setConnectionFactoryClassName("oracle.jdbc.pool.OracleDataSource");
        dataSource.setUser(ORA_USER);
        dataSource.setPassword(ORA_PASSWORD);
        dataSource.setLoginTimeout(10);
        dataSource.setMinPoolSize(0);
        dataSource.setMaxPoolSize(50);
        dataSource.setInitialPoolSize(0);
        dataSource.setInactiveConnectionTimeout(300);//5min
        dataSource.setConnectionPoolName("PLAYGROUND_POOL");
        dataSource.setConnectionProperties(conProp);
        return dataSource;
    }


    public static XADataSource getPoolXADatasource(String url) throws SQLException {
        PoolXADataSource dataSource = PoolDataSourceFactory.getPoolXADataSource();
        dataSource.setURL(url);
        dataSource.setConnectionFactoryClassName("oracle.jdbc.xa.client.OracleXADataSource");
        dataSource.setUser(ORA_USER);
        dataSource.setPassword(ORA_PASSWORD);
        dataSource.setLoginTimeout(10);
        dataSource.setMinPoolSize(0);
        dataSource.setMaxPoolSize(50);
        dataSource.setInitialPoolSize(0);
        dataSource.setInactiveConnectionTimeout(300);//5min
        dataSource.setConnectionPoolName("PLAYGROUND_XA_POOL");
        dataSource.setConnectionProperties(conProp);
        return dataSource;
    }

}
