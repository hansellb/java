package com.springexamples.springbasics;

import com.springexamples.springbasics.beans.UseValuesFromApplicatonProperties;
import com.springexamples.springbasics.config.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringExpressionLanguage {
    public static void main(String args[]) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        UseValuesFromApplicatonProperties myBean = context.getBean(UseValuesFromApplicatonProperties.class);
        myBean.getUndefinedProperty();
        myBean.getBooleanProperty();
        myBean.getIntegerProperty();
        myBean.getStringProperty();
        myBean.getStringArrayProperty();
        myBean.getStringListProperty();
        myBean.getMapProperty();
    }
}
