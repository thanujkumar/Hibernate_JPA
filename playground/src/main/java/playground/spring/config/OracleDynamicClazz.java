package playground.spring.config;

import com.arjuna.ats.internal.jdbc.DynamicClass;
import com.arjuna.ats.jdbc.TransactionalDriver;
import playground.spring.ucp.GlobalOracleConnectionPool;

import javax.sql.XADataSource;
import java.sql.SQLException;

public class OracleDynamicClazz implements DynamicClass {
    private static final String driverName = "oracle:";
    private static final String semicolon = ";";

    private static XADataSource dataSource;

    //This method is called each time when connection is required hence static map to hold the pool
    public XADataSource getDataSource(String dbName) throws SQLException {
        return getDataSource(dbName, true);
    }

    public synchronized XADataSource getDataSource(String dbName, boolean create) throws SQLException {
        if (dataSource != null) {
            return dataSource;
        }


        int index1 = dbName.indexOf(OracleDynamicClazz.driverName);

        if (index1 == -1) {
            throw new SQLException("Oralce.getDataSource - invalid database");
        } else {
            /*
             * Strip off any spurious parameters.
             */
            int index2 = dbName.indexOf(OracleDynamicClazz.semicolon);
            String theDbName = null;

            if (index2 == -1) {
                theDbName = dbName.substring(index1 + OracleDynamicClazz.driverName.length());
            } else {
                theDbName = dbName.substring(index1 + OracleDynamicClazz.driverName.length(), index2);
            }

            System.out.println("URL->" + TransactionalDriver.jdbc + OracleDynamicClazz.driverName + theDbName);
            dataSource = GlobalOracleConnectionPool.getPoolXADatasource(TransactionalDriver.jdbc + OracleDynamicClazz.driverName + theDbName);

            return dataSource;
        }

    }
}
