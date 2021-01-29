package com.springexamples.springbasics.config;

import com.springexamples.springbasics.beans.Bar;
import com.springexamples.springbasics.beans.Foo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecondConfig {
    @Bean(initMethod = "init", destroyMethod = "destroy")
    public Foo foo() {
        return new Foo(bar());
    }

    @Bean
    public Bar bar() {
        return new Bar();
    }
}
