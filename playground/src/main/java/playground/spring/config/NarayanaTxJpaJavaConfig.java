package playground.spring.config;

import com.arjuna.ats.internal.jta.transaction.arjunacore.TransactionSynchronizationRegistryImple;
import com.arjuna.ats.jdbc.TransactionalDriver;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;
import playground.spring.ucp.GlobalOracleConnectionPool;
import playground.utils.UCPDestroy;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

//https://www.baeldung.com/transaction-configuration-with-jpa-and-spring

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {"playground.model","playground.service", "playground.dao","playground.utils","playground.aspects","playground.ws"})
public class NarayanaTxJpaJavaConfig {

    public DataSource dataSource() throws SQLException {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
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


    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() throws SQLException {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setPersistenceUnitName("PLAYGROUND");
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setPersistenceProvider(new HibernatePersistenceProvider());
        return entityManagerFactoryBean;
    }

    @Bean
    public PlatformTransactionManager transactionManager() throws SQLException {
        JtaTransactionManager transactionManager = new JtaTransactionManager();
        //transactionManager.setTransactionSynchronizationRegistry(new TransactionSynchronizationRegistryImple());
        transactionManager.setTransactionManager(new com.arjuna.ats.internal.jta.transaction.arjunacore.TransactionManagerImple());
        transactionManager.setUserTransaction(new com.arjuna.ats.internal.jta.transaction.arjunacore.UserTransactionImple());
        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean(destroyMethod = "destroy")
    public UCPDestroy updCleanUp() {
        return new UCPDestroy();
    }
}
