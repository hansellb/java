package com.springexamples.springboot.config;

import org.springframework.beans.factory.annotation.Autowired;
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
@PropertySource("classpath:multiple-db.properties")
@EnableJpaRepositories(
        basePackages = "com.springexamples.springboot.dao.product",
        entityManagerFactoryRef = "productEntityManagerFactory",
        transactionManagerRef = "productTransactionManager"
)
public class ProductDataSourceConfig {
    @Autowired
    private Environment env;

    @Bean
    public LocalContainerEntityManagerFactoryBean productEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        HibernateJpaVendorAdapter va = new HibernateJpaVendorAdapter();
        HashMap<String, Object> properties = new HashMap<>();

        properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
        properties.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
        properties.put("hibernate.default_schema", "PRODUCTSDB");

        emf.setDataSource(productDataSource());
        emf.setPackagesToScan("com.springexamples.springboot.model.product");
        emf.setJpaVendorAdapter(va);
        emf.setJpaPropertyMap(properties);

        return emf;
    }

    @Bean
    @ConfigurationProperties(prefix="spring.second-datasource") // This lets Spring Boot auto-configuration pick the required properties to create the DataSource
    public DataSource productDataSource() {
//        DriverManagerDataSource ds = new DriverManagerDataSource();
//
//        ds.setDriverClassName(env.getProperty("dataSource.driverClassName"));
//        ds.setUsername(env.getProperty("dataSource.username"));
//        ds.setPassword(env.getProperty("dataSource.password"));
//        ds.setUrl(env.getProperty("dataSource.url"));
//
//        return ds;

        // This is possible due to the @ConfigurationProperties annotation on this method
        // Note: For some reason, the url property MUST be named jdbc-url or jdbcurl, otherwise an error will occur
        return DataSourceBuilder.create().build();
    }

    @Bean
    public PlatformTransactionManager productTransactionManager() {
        JpaTransactionManager tm = new JpaTransactionManager();

        tm.setEntityManagerFactory(productEntityManagerFactory().getObject());

        return tm;
    }
}
