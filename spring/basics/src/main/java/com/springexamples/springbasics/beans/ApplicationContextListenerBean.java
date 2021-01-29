package com.springexamples.springbasics.beans;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

// Any Bean that needs to listen to Spring's ApplicationContext events MUST implement the ApplicationListener interface
// Spring provides the following standard events: https://www.tutorialspoint.com/spring/event_handling_in_spring.htm
// ContextRefreshedEvent:
// ContextStartedEvent:
// ContextStoppedEvent:
// ContextClosedEvent:
// RequestHandledEvent:
// Spring's event handling is single-threaded so if an event is published,
// until and unless all the receivers get the message, the processes are blocked and the flow will not continue.
// Hence, care should be taken when designing your application if the event handling is to be used.
public class ApplicationContextListenerBean implements ApplicationListener {
    public ApplicationContextListenerBean() {
        System.out.println(this.getClass().getSimpleName() + " -> constructor called!!!");
    }

    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        System.out.println("ApplicationEvent -> " + applicationEvent.toString());
    }
}
