package com.springexamples.springbasics.beans;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.Map;

// This Bean will use the @Value annotation to get configuration properties from an properties file
// The file to be used as a source is defined using BOTH the @PropertySource AND the @Configuration annotation - https://www.baeldung.com/properties-with-spring
public class UseValuesFromApplicatonProperties {
    // Using a colon (:) defines a default value
    @Value("${undefined.property:Default value}")
    public String undefinedProperty;

    @Value("${boolean.property}")
    public Boolean booleanProperty;

    @Value("${integer.property}")
    public Integer integerProperty;

    @Value("${string.property}")
    public String stringProperty;

    @Value("${array.property}") // By default, Spring automatically maps comma-separated strings to arrays
    public String[] stringArrayProperty;

    @Value("#{'${array.property}'.split(',')}") // Casting the property to a list, requires using SpEL, fetching it as a string and splitting it using a separator
    public List<String> stringListProperty;

    // https://stackoverflow.com/questions/26275736/how-to-pass-a-mapstring-string-with-application-properties
    @Value("#{${map.property}}")
    public Map<String, String> mapProperty;

    public UseValuesFromApplicatonProperties() {
        // When the constructor code runs, the Bean's properties have not been injected. Therefore, this will print "null"
        System.out.println(this.getClass().getSimpleName() + "-> constructor called!!!");
        System.out.println("Class members annotated with @Value have not been injected yet!!! Member 'property' -> " + this.stringProperty);
    }

    public String getUndefinedProperty() {
        System.out.println(this.getClass().getSimpleName() + " -> getUndefinedProperty: " + this.undefinedProperty);
        return undefinedProperty;
    }

    public Boolean getBooleanProperty() {
        System.out.println(this.getClass().getSimpleName() + " -> getBooleanProperty: " + this.booleanProperty);
        return booleanProperty;
    }

    public Integer getIntegerProperty() {
        System.out.println(this.getClass().getSimpleName() + " -> getIntegerProperty: " + this.integerProperty);
        return integerProperty;
    }

    public String getStringProperty() {
        System.out.println(this.getClass().getSimpleName() + " -> getStringProperty: " + this.stringProperty);
        return stringProperty;
    }

    public String[] getStringArrayProperty() {
        System.out.print(this.getClass().getSimpleName() + " -> getStringArrayProperty: ");
        for(String str : this.stringArrayProperty) {
            System.out.print(str);
        }
        System.out.println();
        return stringArrayProperty;
    }

    public List<String> getStringListProperty() {
        System.out.print(this.getClass().getSimpleName() + " -> getStringListProperty: ");
        this.stringListProperty.stream().forEach(str -> System.out.print(str));
//        Iterator iterator = this.stringListProperty.iterator();
//        while(iterator.hasNext()){
//            System.out.print(iterator.next());
//        }
        System.out.println();
        return stringListProperty;
    }

    public Map<String, String> getMapProperty() {
        System.out.print(this.getClass().getSimpleName() + " -> getMapProperty: ");
        this.mapProperty.forEach((key,value) -> System.out.print("key: " + key + ", value: " + value + "; "));
        return mapProperty;
    }
}
