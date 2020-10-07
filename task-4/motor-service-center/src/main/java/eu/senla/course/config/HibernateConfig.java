package eu.senla.course.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.instrument.classloading.ReflectiveLoadTimeWeaver;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:/hibernate.properties")
public class HibernateConfig {
    @Value("${hibernate.connection.driver_class}")
    private String dbDriver;

    @Value("${hibernate.connection.password}")
    private String dbPassword;

    @Value("${hibernate.connection.url}")
    private String dbUrl;

    @Value("${hibernate.connection.username}")
    private String dbUsername;

    @Value("${hibernate.dialect}")
    private String hibernateDialect;

    @Value("${hibernate.show_sql}")
    private String hibernateShowSql;

    @Value("${hibernate.hbm2ddl.auto}")
    private String hibernateHbm2DdlAuto;

    @Value("${entity_manager.packagesToScan}")
    private String entityManagerPackagesToScan;

    @Value("${hibernate.enable_lazy_load_no_trans}")
    private String hibernateEnableLazyLoadNoTrans;

    @Value("${persistence.unit_name}")
    private String persistenceUnitName;
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(dbDriver);
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(dbUsername);
        dataSource.setPassword(dbPassword);
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager hibernateTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactoryBean().getObject());
        return transactionManager;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", hibernateDialect);
        properties.put("hibernate.show_sql", hibernateShowSql);
        properties.put("hibernate.hbm2ddl.auto", hibernateHbm2DdlAuto);
        properties.put("hibernate.enable_lazy_load_no_trans", hibernateEnableLazyLoadNoTrans);
        return properties;
    }


    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setShowSql(true);

        LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setJpaVendorAdapter(vendorAdapter);
        entityManager.setDataSource(dataSource());
        entityManager.setPackagesToScan(entityManagerPackagesToScan);
        entityManager.setPersistenceUnitName(persistenceUnitName);
        entityManager.setJpaProperties(hibernateProperties());
        entityManager.setLoadTimeWeaver(new ReflectiveLoadTimeWeaver());
        return entityManager;
    }
}
