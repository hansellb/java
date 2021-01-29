package com.springexamples.springbasics;

import com.springexamples.springbasics.beans.HelloWorld;
import com.springexamples.springbasics.config.BasicConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConfigFromAnnotations {
    public static void main(String args[]) {
        // Create a Spring IoC container/context with already registered Beans from classes annotated with @Configuration
        // All Beans (annotated with @Bean) found in the @Configuration annotated class are automatically registered in the context
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BasicConfig.class);

        HelloWorld helloWorld = context.getBean(HelloWorld.class);
        helloWorld.setMessage("Hello World!!!");
    }

}
