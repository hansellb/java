package com.springexamples.springbasics.autowiredbeans;

import org.springframework.stereotype.Component;

@Component
public class SomeBean {
    public SomeBean() {
        System.out.println(this.getClass().getSimpleName() + " -> constructor called!!!");
    }

    public String someMethod(String source) {
        System.out.println(this.getClass().getSimpleName() + " -> someMethod called from '" + source + "'!!!");
        return "someMethod return value!!!";
    }
}
