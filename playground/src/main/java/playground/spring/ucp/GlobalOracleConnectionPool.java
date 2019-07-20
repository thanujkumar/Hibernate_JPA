package playground.spring.ucp;

import oracle.ucp.jdbc.PoolDataSourceFactory;
import oracle.ucp.jdbc.PoolXADataSource;

/**
 * This class provides to create both XA and non-XA connection.
 * <p>
 * For simplicity all properties are hardcoded as single datasource is used
 */
public class GlobalOracleConnectionPool {


    public static void getPoolDatasource() {

    }


    public static void getPoolXADatasource() {
        PoolXADataSource dataSource = PoolDataSourceFactory.getPoolXADataSource();
    }

}
