package com.springexamples.springbasics.autowiredbeans;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

@Component("CustomFooFormatterName")
public class FooFormatter implements Formatter {

    @Override
    public Object parse(String s, Locale locale) throws ParseException {
        System.out.println(this.getClass().getSimpleName() + " -> 'parse' called!!!");
        return null;
    }

    @Override
    public String print(Object o, Locale locale) {
        System.out.println(this.getClass().getSimpleName() + " -> 'print' called!!!");
        return null;
    }
}
