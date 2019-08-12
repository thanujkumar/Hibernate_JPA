package playground.high.performance.config;

import net.ttddyy.dsproxy.ExecutionInfo;
import net.ttddyy.dsproxy.QueryInfo;
import net.ttddyy.dsproxy.listener.logging.DefaultQueryLogEntryCreator;
import net.ttddyy.dsproxy.listener.logging.SLF4JQueryLoggingListener;
import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import playground.spring.ucp.GlobalOracleConnectionPool;
import playground.utils.UCPDestroy;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {"playground.model", "playground.service", "playground.dao", "playground.utils", "playground.high.performance.persistence.support"})
public class HighPerformanceJPAJavaConfig {

    @Bean
    public DataSource dataSource() throws SQLException {
        return GlobalOracleConnectionPool.getPoolDatasource(GlobalOracleConnectionPool.ORA_URL);//non-XA hence RESOURCE_LOCAL

//        SLF4JQueryLoggingListener loggingListener = new SLF4JQueryLoggingListener();
//        loggingListener.setQueryLogEntryCreator(new InlineQueryLogEntryCreator());
//        return ProxyDataSourceBuilder
//                .create(GlobalOracleConnectionPool.getPoolDatasource(GlobalOracleConnectionPool.ORA_URL))
//                .name("GlobalOracleConnectionPool.NonTx")
//                .listener(loggingListener)
//                .build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() throws SQLException {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setPersistenceUnitName("HIGH_PERFORMANCE");
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setPersistenceProvider(new HibernatePersistenceProvider());
        return entityManagerFactoryBean;
    }

    @Bean
    public PlatformTransactionManager transactionManager() throws SQLException {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactoryBean().getObject());
        transactionManager.setJpaDialect(new HibernateJpaDialect());
        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean(destroyMethod = "destroy")
    public UCPDestroy updCleanUp() {
        return new UCPDestroy();
    }
}

//class InlineQueryLogEntryCreator extends DefaultQueryLogEntryCreator {
//    @Override
//    protected void writeParamsEntry(StringBuilder sb, ExecutionInfo execInfo, List<QueryInfo> queryInfoList) {
//        sb.append( "Params:[" );
//        for ( QueryInfo queryInfo : queryInfoList ) {
//            boolean firstArg = true;
//            for ( Map<String, Object> paramMap : queryInfo.getQueryArgsList() ) {
//
//                if ( !firstArg ) {
//                    sb.append( ", " );
//                }
//                else {
//                    firstArg = false;
//                }
//
//                SortedMap<String, Object> sortedParamMap = new TreeMap<>( new StringAsIntegerComparator() );
//                sortedParamMap.putAll( paramMap );
//
//                sb.append( "(" );
//                boolean firstParam = true;
//                for ( Map.Entry<String, Object> paramEntry : sortedParamMap.entrySet() ) {
//                    if ( !firstParam ) {
//                        sb.append( ", " );
//                    }
//                    else {
//                        firstParam = false;
//                    }
//                    Object parameter = paramEntry.getValue();
//                    if ( parameter != null && parameter.getClass().isArray() ) {
//                        sb.append( arrayToString( parameter ) );
//                    }
//                    else {
//                        sb.append( parameter );
//                    }
//                }
//                sb.append( ")" );
//            }
//        }
//        sb.append( "]" );
//    }
//
//    private String arrayToString(Object object) {
//        if ( object.getClass().isArray() ) {
//            if ( object instanceof byte[] ) {
//                return Arrays.toString( (byte[]) object );
//            }
//            if ( object instanceof short[] ) {
//                return Arrays.toString( (short[]) object );
//            }
//            if ( object instanceof char[] ) {
//                return Arrays.toString( (char[]) object );
//            }
//            if ( object instanceof int[] ) {
//                return Arrays.toString( (int[]) object );
//            }
//            if ( object instanceof long[] ) {
//                return Arrays.toString( (long[]) object );
//            }
//            if ( object instanceof float[] ) {
//                return Arrays.toString( (float[]) object );
//            }
//            if ( object instanceof double[] ) {
//                return Arrays.toString( (double[]) object );
//            }
//            if ( object instanceof boolean[] ) {
//                return Arrays.toString( (boolean[]) object );
//            }
//            if ( object instanceof Object[] ) {
//                return Arrays.toString( (Object[]) object );
//            }
//        }
//        throw new UnsupportedOperationException( "Arrat type not supported: " + object.getClass() );
//    }
//}