package com.springexamples.springbasics;

import com.springexamples.springbasics.beans.HelloWorld;
import com.springexamples.springbasics.config.BasicConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class GetSameBeanMultipleTimes {
    public static void main(String args[]) {
        // Create the context (with beans already registered) from a class annotated with @Configuration
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BasicConfig.class);

        // Get the same Bean from the context, multiple times
        // By default, getBean will return the same Bean instance, however,
        // if the Bean is annotated with @Scope("prototype"), getBean will return different instances
        HelloWorld helloWorld1 = context.getBean(HelloWorld.class);
        HelloWorld helloWorld2 = context.getBean(HelloWorld.class);

        helloWorld1.setMessage("Hello World 1");

        // These two lines should output the same message if the Bean scope is "singleton" (by default all beans are
        // annotated with @Scope("singleton")
        // If the Bean scope is "prototype" (@Scope("prototype")), both objects will be different instances, therefore,
        // the second output should contain the word null since the setMessage method has not been called on that instance.
        helloWorld1.getMessage();
        helloWorld2.getMessage();
    }
}
