package com.springexamples.springbasics.beans;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class ContextRefreshedEventHandler implements ApplicationListener<ContextRefreshedEvent> {
    public ContextRefreshedEventHandler() {
        System.out.println(this.getClass().getSimpleName() + " -> constructor called!!!");
    }

    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println("ContextRefreshedEvent -> " + event.toString());
    }
}
