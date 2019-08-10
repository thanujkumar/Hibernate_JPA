package playground.high.performance.persistence.jpa_hibernate;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import playground.high.performance.config.HighPerformanceJPAJavaConfig;
import playground.main.Logging;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Arrays;
import java.util.Date;

/**
 * Raw jdbc not using hibernate though library is loaded
 * https://vladmihalcea.com/hibernate-performance-tuning-tips/
 */
public class UsingJdbcBatchingStatements  extends Logging {
    static AbstractApplicationContext context;

    public static void main(String[] args) throws SQLException {
        context = new AnnotationConfigApplicationContext(HighPerformanceJPAJavaConfig.class);

        //Getting raw connection - Oracle supports batching for PreparedStatements not for java.sql.Statement or java.sql.CallableStatement as required by jdbc spec
        //https://docs.oracle.com/en/database/oracle/oracle-database/18/jjdbc/performance-extensions.html#GUID-24D35E13-A9C0-43F3-8F8B-870AD1BF5339
        DataSource dataSource = context.getBean("dataSource", DataSource.class);
        Connection con = dataSource.getConnection();
        PreparedStatement preparedStatement = con.prepareStatement("insert into REGIONS(REGION_ID, REGION_NAME, CREATED_BY, CREATED_TS, VERSION)" +
                " values (REGIONS_SEQ.nextval,?,?,?,?)",new int[]{1} ); // OR new String[] {"REGION_ID"} to get generated keys
        preparedStatement.setString(1, "Test1");
        preparedStatement.setString(2,"e212731");
        preparedStatement.setTimestamp(3, new Timestamp(new Date().getTime()));
        preparedStatement.setLong(4, 0L);
        preparedStatement.addBatch(); //Add to Batch

        preparedStatement.setString(1, "Test2");
        preparedStatement.setString(2,"e212731");
        preparedStatement.setTimestamp(3, new Timestamp(new Date().getTime()));
        preparedStatement.setLong(4, 0L);
        preparedStatement.addBatch(); //Add to Batch

        preparedStatement.setString(1, "Test3");
        preparedStatement.setString(2,"e212731");
        preparedStatement.setTimestamp(3, new Timestamp(new Date().getTime()));
        preparedStatement.setLong(4, 0L);
        preparedStatement.addBatch(); //Add to Batch

        preparedStatement.setString(1, "Test4");
        preparedStatement.setString(2,"e212731");
        preparedStatement.setTimestamp(3, new Timestamp(new Date().getTime()));
        preparedStatement.setLong(4, 0L);
        preparedStatement.addBatch(); //Add to Batch

        int i[] = preparedStatement.executeBatch(); // executeBatch
        ResultSet rs = preparedStatement.getGeneratedKeys();
        while (rs.next()) {
            System.out.println("Generated Key : "+ rs.getLong(1));
        }

        System.out.println(Arrays.toString(i));

//        Statement statement = con.createStatement();
//        statement.addBatch("");
        con.close();

        context.close();

    }
}
