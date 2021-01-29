package com.springexamples.springbasics.beans;

public class HelloWorld {
    public String message;

    public HelloWorld() {
        System.out.println(this.getClass().getSimpleName() + " -> constructor called!!!");
    }

    public String getMessage() {
        System.out.println("Message set to: " + this.message);
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
