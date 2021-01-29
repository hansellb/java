package com.springexamples.springboot.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

// https://scattercode.co.uk/2013/11/18/spring-data-multiple-databases/
// https://github.com/gratiartis/multids-demo

@Configuration
@ComponentScan(basePackages = "com.springexamples.springboot")
public class AppConfig {
}
