package com.springexamples.springbasics.config;

import com.springexamples.springbasics.beans.HelloWorld;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration // This annotation tells Spring that this class can be used to get Beans
@Import(SecondConfig.class) // This annotation allows loading Beans from another configuration class. These other Bean constructors will be called BEFORE the ones in this class.
public class FirstConfig {

    @Bean
    public HelloWorld helloWorld() {
        return new HelloWorld();
    }
}
