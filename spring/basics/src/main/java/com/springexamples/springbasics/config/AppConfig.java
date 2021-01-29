package com.springexamples.springbasics.config;

import com.springexamples.springbasics.beans.UseValuesFromApplicatonProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

// Making a property file available to beans - https://www.journaldev.com/21440/spring-propertysource
// Using dymanic variables to get the right file at runtime
//@PropertySource("classpath:app_${dynamic_var:default}.props")
// Setting multiple property sources can be made using multiple @PropertySource annotations or with the @PropertySources
//@PropertySources({
//        @PropertySource("classpath:first_prop_file"),
//        @PropertySource("classpath:second_prop_file")
//})

@Configuration
@PropertySource("app.props")
public class AppConfig {
    @Autowired
    private Environment env;

    @Bean
    public UseValuesFromApplicatonProperties useValuesFromApplicatonProperties() {
        return new UseValuesFromApplicatonProperties();
    }
}
