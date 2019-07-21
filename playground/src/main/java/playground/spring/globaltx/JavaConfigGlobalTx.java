package playground.spring.globaltx;

import com.arjuna.ats.internal.jta.transaction.arjunacore.TransactionSynchronizationRegistryImple;
import com.arjuna.ats.jdbc.TransactionalDriver;
import oracle.ucp.UniversalConnectionPool;
import oracle.ucp.admin.UniversalConnectionPoolManagerImpl;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.DefaultPersistenceUnitManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;
import playground.spring.ucp.GlobalOracleConnectionPool;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {"playground.model", "playground.service", "playground.dao"})
public class JavaConfigGlobalTx implements DestructionAwareBeanPostProcessor {

    //Required if mulitple persistence units exists
    public final static String PERSISTENCE_UNIT_NAME = "PLAYGROUND_JTA";

    @Bean
    public DataSource dataSource() throws SQLException {
        DriverManagerDataSource driverManagerDataSource = new UCPDataSource();
        driverManagerDataSource.setDriverClassName("com.arjuna.ats.jdbc.TransactionalDriver");
        driverManagerDataSource.setUrl(GlobalOracleConnectionPool.NARAYANA_ORA_URL);
        driverManagerDataSource.setUsername(GlobalOracleConnectionPool.ORA_USER);
        driverManagerDataSource.setPassword(GlobalOracleConnectionPool.ORA_PASSWORD);
        //Following properties are required so that each time details are passed for Narayana TransactionDriver
        Properties prop = new Properties();
        prop.setProperty(TransactionalDriver.userName, GlobalOracleConnectionPool.ORA_USER);
        prop.setProperty(TransactionalDriver.password, GlobalOracleConnectionPool.ORA_PASSWORD);
        prop.setProperty(TransactionalDriver.poolConnections, "false");
        prop.setProperty(TransactionalDriver.dynamicClass, OracleDynamicClazz.class.getName());
        driverManagerDataSource.setConnectionProperties(prop);
        return driverManagerDataSource;
    }

    /**
     * This doesn't need persistence.xml as packagesToScan is provided and also all required properties are set here
     * <p>
     * <link>https://www.baeldung.com/the-persistence-layer-with-spring-and-jpa</link>
     *
     * @return
     * @throws SQLException
     */
    @Bean
    @DependsOn("dataSource")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() throws SQLException {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setPersistenceUnitManager(defaultPersistenceUnitManager()); //If you need to load specific xml and then package scan to be disabled
       // entityManagerFactoryBean.setPersistenceUnitName(PERSISTENCE_UNIT_NAME);
        //entityManagerFactoryBean.setPackagesToScan("playground.model"); //where all entities exits
        entityManagerFactoryBean.setJtaDataSource(dataSource()); //Need to set JTA source if need to be in transaction, else local transaction considered
        entityManagerFactoryBean.setJpaVendorAdapter(new org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setPersistenceProvider(new HibernatePersistenceProvider());
        //Set all hibernate properties here as no persistence.xml is used (spring 3.1 and above)
        Properties jpaProp = new Properties();
        jpaProp.setProperty("hibernate.dialect", "org.hibernate.dialect.Oracle12cDialect");
        jpaProp.setProperty("hibernate.query.fail_on_pagination_over_collection_fetch", "true");
        //jpaProp.setProperty("hibernate.format_sql", "true");
        //jpaProp.setProperty("hibernate.generate_statistics", "true");
        entityManagerFactoryBean.setJpaProperties(jpaProp);
        return entityManagerFactoryBean;
    }

    @Bean
    public PlatformTransactionManager transactionManager() throws Exception {
        JtaTransactionManager transactionManager = new JtaTransactionManager();
        transactionManager.setTransactionSynchronizationRegistry(new TransactionSynchronizationRegistryImple());
        transactionManager.setTransactionManager(new com.arjuna.ats.internal.jta.transaction.arjunacore.TransactionManagerImple());
        transactionManager.setUserTransaction(new com.arjuna.ats.internal.jta.transaction.arjunacore.UserTransactionImple());
        return transactionManager;
    }

    public DefaultPersistenceUnitManager defaultPersistenceUnitManager() throws SQLException {
        DefaultPersistenceUnitManager defaultPersistenceUnitManager = new DefaultPersistenceUnitManager();
        defaultPersistenceUnitManager.setPersistenceXmlLocation("classpath*:META-INF/persistence-jta.xml");
        return defaultPersistenceUnitManager;
    }

    @Override
    public void postProcessBeforeDestruction(Object bean, String beanName) throws BeansException {
        System.out.println("Destroying : " + beanName + " instance -> " + bean);
    }


    public class UCPDataSource extends DriverManagerDataSource implements DisposableBean {

        @Override
        public void destroy() throws Exception {
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
    }

}
