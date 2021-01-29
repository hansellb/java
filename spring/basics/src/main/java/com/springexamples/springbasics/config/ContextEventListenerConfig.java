package com.springexamples.springbasics.config;

import com.springexamples.springbasics.beans.ApplicationContextListenerBean;
import com.springexamples.springbasics.beans.ContextRefreshedEventHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContextEventListenerConfig {
    @Bean
    public ApplicationContextListenerBean applicationContextListenerBean() {
        return new ApplicationContextListenerBean();
    }

    @Bean
    public ContextRefreshedEventHandler contextRefreshedEventHandler() {
        return new ContextRefreshedEventHandler();
    }
}
