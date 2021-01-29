package com.springexamples.springbasics;

import com.springexamples.springbasics.beans.HelloWorld;
import org.springframework.context.support.ClassPathXmlApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ConfigFromXmlFIle {
    public static void main(String args[]) {
        // Create the Spring IoC container / context from a configuration file - https://www.baeldung.com/spring-classpathxmlapplicationcontext
//        ApplicationContext ctx = new ClassPathXmlApplicationContext("FirstConfig.xml", "SecondConfig.xml");
        // Using the a specific implementation of ApplicationContext, gives access to event publishing methods such as ctx.refresh()/start()/stop(), etc.
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("FirstConfig.xml", "SecondConfig.xml");

        // Getting a Bean from the ApplicationContext will return a generic Object which MUST be casted to the appropriate type
        // Notice that with XML-based configuration, the Bean property can be set in the XML file... No need to call setter methods
        HelloWorld helloWorld = (HelloWorld) ctx.getBean("helloWorld");
        helloWorld.getMessage();

        // Get the bean using class name
        HelloWorld helloWorld2 = (HelloWorld) ctx.getBean("helloWorld2");
        helloWorld2.getMessage();

        // This will throw an error because in both config.xml files, there are Beans that have the same class attribute.
        // Using the id attribute instead, as the two examples above, solves this problem
//        HelloWorld hW = ctx.getBean(HelloWorld.class);
//        hW.getMessage();
    }
}
