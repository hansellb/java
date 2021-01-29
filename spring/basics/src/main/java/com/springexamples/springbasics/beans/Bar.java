package com.springexamples.springbasics.beans;

public class Bar {
    public Bar() {
        System.out.println(this.getClass().getSimpleName() + " -> constructor called!!!");
    }

    public void doSomething() {
        System.out.println(this.getClass().getSimpleName() + " -> doSomething!!!");
    }
}
