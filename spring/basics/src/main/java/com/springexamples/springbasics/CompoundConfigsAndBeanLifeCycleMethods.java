package com.springexamples.springbasics;

import com.springexamples.springbasics.beans.Foo;
import com.springexamples.springbasics.beans.HelloWorld;
import com.springexamples.springbasics.config.FirstConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CompoundConfigsAndBeanLifeCycleMethods {
    public static void main(String args[]) {
        // Create context with Beans defined in a class annotated with @Configuration that imports (using @Import)
        // Beans from another class, also annotated with @Configuration
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(FirstConfig.class);

        // The HelloWorld bean is in one configuration class
        System.out.println("Getting HelloWorld Bean from ApplicationContext...");
        HelloWorld helloWorld = context.getBean(HelloWorld.class);

        // The Foo & Bar beans are in another configuration class
        // Notice also that the Foo Bean depends on the Bar bean
        // Furthermore, the Foo Bean has custom Bean lifecycle methods
        System.out.println("Getting Foo Bean from ApplicationContext...");
        Foo foo = context.getBean(Foo.class);
        foo.doSomething();

        // This is needed to trigger Beans' destroy method
        context.close();
    }
}
