package com.springexamples.springbasics.annotatedBeans;

import org.springframework.stereotype.Component;

@Component
public class AnnotatedBean {
    private String prop;

    public AnnotatedBean() {
        System.out.println(this.getClass().getSimpleName() + " -> constructor called!!!");
    }

    public String getProp() {
        System.out.println(this.getClass().getSimpleName() + " -> getProp called!!!");
        return prop;
    }

    public void setProp(String prop) {
        this.prop = prop;
    }
}
