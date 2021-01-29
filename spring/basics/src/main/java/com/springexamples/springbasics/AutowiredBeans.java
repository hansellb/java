package com.springexamples.springbasics;

import com.springexamples.springbasics.autowiredbeans.AutowiredBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.Locale;

@ComponentScan(basePackages = "com.springexamples.springbasics.autowiredbeans")
public class AutowiredBeans {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AutowiredBeans.class);

//        for(String beanName : context.getBeanDefinitionNames()) {
//            System.out.println(beanName);
//        }

        AutowiredBean autowiredBean = context.getBean(AutowiredBean.class);
        autowiredBean.getFormatter().print("", new Locale("en"));
    }
}
