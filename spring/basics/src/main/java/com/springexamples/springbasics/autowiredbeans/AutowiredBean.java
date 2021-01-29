package com.springexamples.springbasics.autowiredbeans;

import com.springexamples.springbasics.beans.HelloWorld;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

@Component
public class AutowiredBean {

    // When @Autowired is used on properties, Spring injects the propery/class object when the Bean class is created
    // It eliminates the need for setters/getters
    @Autowired
    private SomeBean someBean;

    // When there are two or more Beans / classes that implement the same interface, the @Qualifier annotation MUST be
    // used along with @Autowired. The @Qualifier argument should be a Bean ID String. The Bean ID can be taken from
    // the class name, in camel-case format, or by using an argument to the Bean's @Component annotation, e.g., @Component("CustomBeanName")
//    @Qualifier("barFormatter") // Bean ID is taken from camel-case bean name
    @Qualifier("CustomFooFormatterName") // Bean ID is taken from @Component argument
    @Autowired
    private Formatter formatter;

    // No-arg constructor
    public AutowiredBean(){
        System.out.println(this.getClass().getSimpleName() + " -> constructor called!!!");
        try {
            this.someBean.someMethod(this.getClass().getSimpleName() + "constructor");
        } catch (Exception e) {
            System.out.println(e.toString() + " when SomeBean -> someMethod called from "
                    + this.getClass().getSimpleName() + " -> constructor (NO-ARGs) "
                    + "because a class property is injected AFTER the constructor is called!!!");
        }

    }

    // When @Autowired is used on a constructor that has other beans as parameters, Spring creates and injects
    // those beans into the constructor. Additionally, Spring does not use the no-args constructor is not used
    @Autowired
    public AutowiredBean(SomeBean someBean) {
        System.out.println(this.getClass().getSimpleName() + " -> constructor (with @Autowired / injected Bean) called!!!");
        someBean.someMethod(this.getClass().getSimpleName() + " -> constructor (with @Autowired / injected Bean)");
    }

    // When @Autowired is used on a setter method that has another bean as parameter, the setter method is called after
    // the constructor is called. Spring creates (calling the constructor) and injects the appropriate bean into the setter method
    @Autowired
    public void setSomeBean(SomeBean someBean){
        System.out.println(this.getClass().getSimpleName() + " -> setter method 'setSomeBean' (with @Autowired) called!!!");
        someBean.someMethod(this.getClass().getSimpleName() + " -> setter method 'setSomeBean' (with @Autowired)");
        this.someBean = someBean;
    }

    public SomeBean getSomeBean(){
        System.out.println(this.getClass().getSimpleName() + " -> getSomeBean called!!!");
        return this.someBean;
    }

    public Formatter getFormatter() {
        return formatter;
    }
}
