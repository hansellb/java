package com.springexamples.springbasics;

import com.springexamples.springbasics.beans.HelloWorld;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConfigManuallyByRegisteringBeans {
    public static void main(String args[]) {
        // Create s Spring IoC container/context with an already registered bean or empty
        //AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(HelloWorld.class);
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();

        // These two lines are required if the AnnotationConfigApplicationContext is called WITHOUT arguments...
        // The Beans / classes MUST be registered in the application context AND it MUST be refreshed before getting Beans from it
        ctx.register(HelloWorld.class);
        ctx.refresh();

        // Get the bean using the bean id string (should be the Bean's class name in camel-case form)
//        HelloWorld helloWorld = (HelloWorld) ctx.getBean("helloWorld");
        // Get the bean using the Bean's class. Using this method there is no need to cast the result from getBean
        HelloWorld helloWorld = ctx.getBean(HelloWorld.class);

        // If the property setter method is not called, the getter method will return "null"
        helloWorld.setMessage("Hello World!!!");
        helloWorld.getMessage();
    }
}
