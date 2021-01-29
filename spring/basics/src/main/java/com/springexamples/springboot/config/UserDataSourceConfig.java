package com.springexamples.springboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@PropertySource("classpath:multiple-db.properties") // Indicates from where the Spring Environment will be injected
@EnableJpaRepositories(
        basePackages = "com.springexamples.springboot.dao.user",
        entityManagerFactoryRef = "userEntityManagerFactory",
        transactionManagerRef = "userTransactionManager"
)
public class UserDataSourceConfig {
    @Autowired // If present, the Spring Environment will be injected from the file specified in the @PropertySource annotation
    private Environment env;

    @Bean
    @Primary // Indicates that this should be the primary Bean to use in case two or more Beans with the same type are found
    public LocalContainerEntityManagerFactoryBean userEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        HibernateJpaVendorAdapter va = new HibernateJpaVendorAdapter();
        HashMap<String, Object> properties = new HashMap<>();

        properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
        properties.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
        properties.put("hibernate.default_schema", "USERSDB");

        emf.setDataSource(userDataSource());
        emf.setPackagesToScan("com.springexamples.springboot.model.user");
        emf.setJpaVendorAdapter(va);
        emf.setJpaPropertyMap(properties);

        return emf;
    }

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource") // This lets Spring Boot auto-configuration pick the required properties to create the DataSource
    public DataSourceProperties userDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    public DataSource userDataSource() {
//        DriverManagerDataSource ds = new DriverManagerDataSource();
//
//        ds.setDriverClassName(env.getProperty("dataSource.driverClassName"));
//        ds.setUsername(env.getProperty("dataSource.username"));
//        ds.setPassword(env.getProperty("dataSource.password"));
//        ds.setUrl(env.getProperty("dataSource.url"));
//
//        return ds;

        // This is possible due to the @ConfigurationProperties annotation on this method
        return userDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean
    @Primary
    public PlatformTransactionManager userTransactionManager() {
//        JpaTransactionManager tm = new JpaTransactionManager();
//        tm.setEntityManagerFactory(userEntityManagerFactory().getObject());
//        return tm;
        return new JpaTransactionManager(userEntityManagerFactory().getObject());
    }
}
