package com.springexamples.springbasics.config;

import com.springexamples.springbasics.beans.HelloWorld;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class BasicConfig {
    @Bean
    @Scope("prototype") // The default value is "singleton", which makes the getBean method return always the same instance.
    public HelloWorld helloWorld() {
        return new HelloWorld();
    }
}
