package org.senla.share.hibernate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.instrument.classloading.ReflectiveLoadTimeWeaver;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class HibernateConfiguration {
    @Value("${hibernate.connection.driver_class}")
    private String dbDriver;

    @Value("${hibernate.connection.password}")
    private String dbPassword;

    @Value("${hibernate.connection.url}")
    private String dbUrl;

    @Value("${hibernate.connection.username}")
    private String dbUsername;

    @Value("${entity_manager.packagesToScan}")
    private String entityManagerPackagesToScan;

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

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setShowSql(true);

        LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setJpaVendorAdapter(vendorAdapter);
        entityManager.setDataSource(dataSource());
        entityManager.setPackagesToScan(entityManagerPackagesToScan);
        entityManager.setPersistenceUnitName(persistenceUnitName);
        entityManager.setLoadTimeWeaver(new ReflectiveLoadTimeWeaver());
        return entityManager;
    }
}
