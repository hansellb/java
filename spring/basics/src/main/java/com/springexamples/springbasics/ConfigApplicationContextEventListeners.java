package com.springexamples.springbasics;

import com.springexamples.springbasics.config.ContextEventListenerConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConfigApplicationContextEventListeners {
    public static void main(String args[]) {
        // Create the context (with beans already registered) from a class annotated with @Configuration
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        // If we pass a class annotated with @Configuration, all Beans (annotated with @Bean) found are registered automatically, the
        // context.refresh() method is called automatically and an IllegalStateException is thrown if .refresh() method is called again
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ContextEventListenerConfig.class);

        // Beans are automatically registered in the context when using @Configuration annotated classes
//        context.register(ContextRefreshedEventHandler.class);
//        context.register(ApplicationContextListenerBean.class);

        // The context.refresh() method is called automatically when using @Configuration annotated classes
        // Triggers the ContextRefreshedEvent
//        context.refresh();

        // Triggers the ContextStartedEvent
        context.start();

        // Triggers the ContextStoppedEvent
        context.stop();

        // Triggers the ContextClosedEvent
        context.close();
    }
}
