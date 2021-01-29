package com.springexamples.springbasics;

import com.springexamples.springbasics.annotatedBeans.AnnotatedBean;
import com.springexamples.springbasics.beans.Bar;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

// The @ComponentScan annotation tells Spring that it should look for Beans in the package where the annotation is
// is present and all its sub-packages. If specific packages need to be scanned use either:
// https://www.baeldung.com/spring-component-scanning
// https://dzone.com/articles/spring-component-scan
//@ComponentScan("my.specific.package.name")
//@ComponentScans({"my.first.package.name", "my.second.package.name"})
//@ComponentScan(basePackages = {
//        "guru.springframework.blog.componentscan.example.demopackageA",
//        "guru.springframework.blog.componentscan.example.demopackageD",
//        "guru.springframework.blog.componentscan.example.demopackageE"
//    },
//    basePackageClasses = DemoBeanB1.class)
//@ComponentScan(value = "guru.springframework.blog.componentscan.example.demopackageA",
//        useDefaultFilters = false)
//@ComponentScan(basePackages = {"guru.springframework.blog.componentscan.example.demopackageA",
//        "guru.springframework.blog.componentscan.example.demopackageB"},
//        includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = DemoBeanB2.class),
//        useDefaultFilters = false)
//@ComponentScan(useDefaultFilters = false, includeFilters = @ComponentScan.Filter
//        (type = FilterType.REGEX, pattern = ".*[A2]"))
//@ComponentScan(basePackageClasses = {DemoBeanB1.class},
//        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
//                value = DemoBeanB2.class))
//The available FilterType values are:
//- FilterType.ANNOTATION: Include or exclude those classes with a stereotype annotation
//- FilterType.ASPECTJ: Include or exclude classes using an AspectJ type pattern expression
//- FilterType.ASSIGNABLE_TYPE: Include or exclude classes that extend or implement this class or interface
//- FilterType.REGEX: Include or exclude classes using a regular expression
//- FilterType.CUSTOM: Include or exclude classes using a custom implementation of the org.springframework.core.type.TypeFilter interface
@ComponentScan("com.springexamples.springbasics.annotatedBeans")
//@Configuration // This should always be present with @ComponentScan, however, without this annotation, the app works
public class ScanningComponents {

    // Even though the Bar class does not have the @Component annotation, Beans will be registered in the context
    // when annotated with @Bean annotation. This happens if this class is passed as an argument to the
    // AnnotationConfigApplicationContext method.
    @Bean
    public Bar beanFromBar() {
        return new Bar();
    }

    public static void main(String args[]) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ScanningComponents.class);

        // This Bean is not available since it is not annotated with @Component
//        HelloWorld helloWorld = context.getBean(HelloWorld.class);
//        helloWorld.setMessage("Hello World!!!");
//        helloWorld.getMessage();

        for (String beanDefinitionName : context.getBeanDefinitionNames()) {
            System.out.println("Context containsBean '" + beanDefinitionName + "': " + context.containsBean(beanDefinitionName) + "," + context.containsBeanDefinition(beanDefinitionName));
        }

//        String str = "Context containsBean '" + AnnotatedBean.class.getSimpleName() + "': ";
//        System.out.println(str + context.containsBean("annotatedBean"));
//        System.out.println(str + context.containsBean(HelloWorld.class.toString()));
//        str = "Context containsBeanDefinition '" + AnnotatedBean.class.getSimpleName() + "': ";
//        System.out.println(str + context.containsBeanDefinition("annotatedBean")); // Using AnnotatedBean.class.toString() will not work
//        System.out.println(str + context.containsBeanDefinition("helloWorld"));
        AnnotatedBean annotatedBean = context.getBean(AnnotatedBean.class);
        annotatedBean.getProp();
    }
}
