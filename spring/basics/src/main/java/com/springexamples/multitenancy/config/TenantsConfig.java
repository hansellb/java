package com.springexamples.multitenancy.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "app")
@PropertySource(value = "classpath:multitenancy.yml", factory = YamlPropertySourceFactory.class)
@Setter
@Getter
public class TenantsConfig {
    private Map<Object, Object> tenants;
}
