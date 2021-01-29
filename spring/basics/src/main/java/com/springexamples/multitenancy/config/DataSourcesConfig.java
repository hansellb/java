package com.springexamples.multitenancy.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

@Configuration
@PropertySource(value = "classpath:multitenancy.yml", factory = YamlPropertySourceFactory.class)
@ComponentScan(basePackages = "com.springexamples.multitenancy")
@EnableJpaRepositories(
        basePackages = "com.springexamples.multitenancy.repository",
        entityManagerFactoryRef = "multiTenantEntityManagerFactory",
        transactionManagerRef = "multiTenantTransactionManager"
)
@EnableTransactionManagement
public class DataSourcesConfig {
    private final String PACKAGE_SCAN = "com.springexamples.multitenancy.entity";
    private static final Logger logger = LoggerFactory.getLogger(DataSourcesConfig.class.getName());
    private TenantsConfig tenantsConfig;

    @Autowired
    private Environment env;

    public DataSourcesConfig(final TenantsConfig tenantsConfig) {
        this.tenantsConfig = tenantsConfig;
    }

    @PostConstruct
    private void postConstruct() {
//        System.out.println("\n**************************************************");
//        Map<String, Map<Object, Object>> tenants = (Map) this.tenantsConfig.getTenants();
//        tenants.forEach(
//                (key, value) -> {
//                    Map<String, String> datasourceProps = (Map<String, String>) value.get("datasource");
//
//                    System.out.println("key: " + key + "(" + key.getClass() + ") | value: " + value + "(" + value.getClass() + ")");
//                    System.out.println(value.get("datasource") + " -> " + value.get("datasource").getClass());
//                }
//        );
//        System.out.println("**************************************************\n");
    }

    @Bean(name = "multiRoutingDataSource")
    @Primary
    public DataSource dataSourceRouter() {
        Map<Object, Object> dataSources = new HashMap<>();
        Map tenants = this.tenantsConfig.getTenants();

        tenants.forEach(
                (tenant, props) -> {
                    Map<String, Object> tenantProps = (Map<String, Object>) props;
                    Map<String, String> datasourceProps = (Map<String, String>) tenantProps.get("datasource");
                    DataSourceBuilder dsb = DataSourceBuilder.create();
                    dsb.driverClassName(datasourceProps.get("driverClassName"));
                    dsb.url(datasourceProps.get("jdbcUrl"));
                    dsb.username(datasourceProps.get("username"));
                    dsb.password(datasourceProps.get("password"));
                    dataSources.put(tenant, dsb.build());
                }
        );

        DataSourceRouter dataSourceRouter = new DataSourceRouter();
        dataSourceRouter.setDefaultTargetDataSource(dataSources.get("es"));
        dataSourceRouter.setTargetDataSources(dataSources);

        return dataSourceRouter;
    }

    @Bean(name = "multiTenantEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManager() {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

        emf.setDataSource(dataSourceRouter());
        emf.setPackagesToScan(PACKAGE_SCAN);
        emf.setJpaVendorAdapter(vendorAdapter);
        emf.setJpaProperties(hibernateProperties());
        return emf;
    }

    @Bean(name = "multiTenantTransactionManager")
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManager().getObject());
        return transactionManager;
    }

//    @Bean(name = "multiTenantTransactionManager")
//    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
//        DataSourceTransactionManager dstm = new DataSourceTransactionManager();
//        dstm.setDataSource(dataSource);
//        return dstm;
//    }

//    @Bean(name = "multiTenantSessionFactory")
//    public LocalSessionFactoryBean sessionFactory() {
//        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
//        sessionFactoryBean.setDataSource(dataSourceRouter());
//        sessionFactoryBean.setPackagesToScan(PACKAGE_SCAN);
//        sessionFactoryBean.setHibernateProperties(hibernateProperties());
//        return sessionFactoryBean;
//    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();

        properties.put("hibernate.dialect", Objects.requireNonNull(env.getProperty("hibernate.dialect")));
        properties.put("hibernate.hbm2ddl.auto", Objects.requireNonNull(env.getProperty("hibernate.hbm2ddl.auto")));
        properties.put("hibernate.default_schema", Objects.requireNonNull(env.getProperty("hibernate.default_schema")));
        properties.put("hibernate.format_sql", Objects.requireNonNull(env.getProperty("hibernate.format_sql")));
        properties.put("hibernate.show_sql", Objects.requireNonNull(env.getProperty("hibernate.show_sql")));
        properties.put("hibernate.physical_naming_strategy", Objects.requireNonNull(env.getProperty("hibernate.physical_naming_strategy")));
        properties.put("hibernate.implicit_naming_strategy", Objects.requireNonNull(env.getProperty("hibernate.implicit_naming_strategy")));

        return properties;
    }
}
