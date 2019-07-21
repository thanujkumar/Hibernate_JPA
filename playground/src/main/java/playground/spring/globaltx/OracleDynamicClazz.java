package playground.spring.globaltx;

import com.arjuna.ats.internal.jdbc.DynamicClass;
import com.arjuna.ats.jdbc.TransactionalDriver;
import oracle.ucp.jdbc.PoolDataSourceFactory;
import oracle.ucp.jdbc.PoolXADataSource;
import playground.spring.ucp.GlobalOracleConnectionPool;

import javax.sql.DataSource;
import javax.sql.XAConnection;
import javax.sql.XADataSource;
import javax.transaction.xa.XAException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.HashMap;
import java.util.logging.Logger;

//https://developer.jboss.org/wiki/NarayanaTransactionsTroubleshootingGuidedraft

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
            dataSource =  new WrapperXAConnection(GlobalOracleConnectionPool.getPoolXADatasource(TransactionalDriver.jdbc + OracleDynamicClazz.driverName + theDbName));

            return dataSource;
        }

    }


    class WrapperXAConnection implements XADataSource {

        XADataSource xaDataSource;
        WrapperXAConnection(XADataSource _xaDataSource) {
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>XADatasource "+ _xaDataSource.hashCode());
            xaDataSource = _xaDataSource;
        }

        @Override
        public XAConnection getXAConnection() throws SQLException {
            XAConnection xaConnection = xaDataSource.getXAConnection();
            System.out.println("getXAConnection()>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>XAConnection "+ xaConnection.hashCode());
            return xaConnection;
        }

        @Override
        public XAConnection getXAConnection(String user, String password) throws SQLException {
            XAConnection xaConnection = xaDataSource.getXAConnection(user, password);
            try {
                System.out.println("Tx Timeout : " + xaConnection.getXAResource().getTransactionTimeout());
            } catch (XAException e) {
                e.printStackTrace();
            }
            System.out.println("getXAConnection("+user+","+password+")>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>XAConnection "+ xaConnection.hashCode());
            return xaConnection;
        }

        @Override
        public PrintWriter getLogWriter() throws SQLException {
            return xaDataSource.getLogWriter();
        }

        @Override
        public void setLogWriter(PrintWriter out) throws SQLException {
          xaDataSource.setLogWriter(out);
        }

        @Override
        public void setLoginTimeout(int seconds) throws SQLException {
           xaDataSource.setLoginTimeout(seconds);
        }

        @Override
        public int getLoginTimeout() throws SQLException {
            return xaDataSource.getLoginTimeout();
        }

        @Override
        public Logger getParentLogger() throws SQLFeatureNotSupportedException {
            return xaDataSource.getParentLogger();
        }
    }
}