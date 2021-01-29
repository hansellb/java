package com.springexamples.springbasics.beans;

public class Foo {
    private Bar bar;

    public Foo(Bar bar) {
        System.out.println(this.getClass().getSimpleName() + " -> constructor called!!! -> Parameter " + bar.getClass().getSimpleName());
        this.bar = bar;
    }

    public void doSomething() {
        System.out.println(this.getClass().getSimpleName() + " -> doSomething!!!");
        bar.doSomething();
    }

    public void init() {
        System.out.println(this.getClass().getSimpleName() + " -> init!!!");
    }

    public void destroy() {
        System.out.println(this.getClass().getSimpleName() + " -> destroy!!!");
    }
}
