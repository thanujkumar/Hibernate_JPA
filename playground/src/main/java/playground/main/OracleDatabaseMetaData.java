package playground.main;

import playground.spring.ucp.GlobalOracleConnectionPool;
import playground.utils.UCPDestroy;

import javax.sql.DataSource;
import javax.transaction.Transaction;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class OracleDatabaseMetaData {

	public static void main(String[] args) throws SQLException {
		DataSource ds = GlobalOracleConnectionPool.getPoolDatasource(GlobalOracleConnectionPool.ORA_URL);
		Connection conn = null;
		try {

			conn = ds.getConnection();
			// Get DatabaseMetaData object
			DatabaseMetaData dbmd = conn.getMetaData();
			System.out.println("About the database...");
			String dbName = dbmd.getDatabaseProductName();
			String dbVersion = dbmd.getDatabaseProductVersion();
			String dbURL = dbmd.getURL();
			System.out.println("Database Name:" + dbName);
			System.out.println("Database Version:" + dbVersion);
			System.out.println("Database URL:" + dbURL);
			System.out.println("Max Table name length:" + dbmd.getMaxTableNameLength());
			System.out.println("Max Column name length:" + dbmd.getMaxColumnNameLength());
			System.out.println("Max Row Size:" + dbmd.getMaxRowSize());
			System.out.println("Default Holdability (the result set scope in regard to a transaction lifecycle):" + conn.getHoldability()); //ResultSet.HOLD_CURSORS_OVER_COMMIT
			System.out.println("Autocommit as set in UCP - connection property :"+ conn.getAutoCommit());
			System.out.println("Transaction Isolation :"+ conn.getTransactionIsolation());//Connection.TRANSACTION_READ_COMMITTED


			System.out.printf("%nAbout JDBC driver...%n");
			String driverName = dbmd.getDriverName();
			String driverVersion = dbmd.getDriverVersion();
			int jdbcMajorVersion = dbmd.getJDBCMajorVersion();
			int jdbcMinorVersion = dbmd.getJDBCMinorVersion();
			System.out.println("Driver Name:" + driverName);
			System.out.println("Driver Version:" + driverVersion);
			System.out.println("JDBC Spec Version:" + jdbcMajorVersion + "." + jdbcMinorVersion);

			System.out.printf("%nAbout supported features...%n");
			boolean ansi92BiEntry = dbmd.supportsANSI92EntryLevelSQL();
			boolean ansi92Intermediate = dbmd.supportsANSI92IntermediateSQL();
			boolean ansi92Full = dbmd.supportsANSI92FullSQL();
			boolean supportsBatchUpdates = dbmd.supportsBatchUpdates();
			System.out.println("Supports Core SQL Grammer:" + dbmd.supportsCoreSQLGrammar());
			System.out.println("Supports Entry Level ANSI92 SQL:" + ansi92BiEntry);
			System.out.println("Supports Intermediate Level ANSI92 SQL:" + ansi92Intermediate);
			System.out.println("Supports Full Level ANSI92 SQL:" + ansi92Full);
			System.out.println("Supports batch updates:" + supportsBatchUpdates);

			System.out.println("Supports Batch Updates:" + dbmd.supportsBatchUpdates());
			System.out.println("Supports Save Points:" + dbmd.supportsSavepoints());
			System.out.println("Supports Transactions:" + dbmd.supportsTransactions());
			System.out.println("Supports Stored Procedure:" + dbmd.supportsStoredProcedures());
			System.out.println("Supports TRANSACTION_READ_COMMITTED:" + dbmd.supportsTransactionIsolationLevel(Connection.TRANSACTION_READ_COMMITTED));
			System.out.println("Supports TRANSACTION_READ_UNCOMMITTED:" + dbmd.supportsTransactionIsolationLevel(Connection.TRANSACTION_READ_UNCOMMITTED));
			System.out.println("Supports TRANSACTION_REPEATABLE_READ:" + dbmd.supportsTransactionIsolationLevel(Connection.TRANSACTION_REPEATABLE_READ));
			System.out.println("Supports TRANSACTION_SERIALIZABLE:" + dbmd.supportsTransactionIsolationLevel(Connection.TRANSACTION_SERIALIZABLE));
			System.out.println("Supports TRANSACTION_NONE:" + dbmd.supportsTransactionIsolationLevel(Connection.TRANSACTION_NONE));

			//Connection client info
			Properties properties = conn.getClientInfo();
			properties.list(System.out);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				new UCPDestroy().destroy();
			} catch (Exception e) {
				e.printStackTrace();

			}
		}

	}
}
