package com.springexamples.springboot.model.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

// Note that this model is in its own package. This is required when configuring the data source as the annotation
// @EnableJpaRepositories requires the "basePackages" properties which should be the package which contains ONLY those
// entities that will interact with the datasource
@Entity // Marks this class for JPA to use it as a DB table
@Table(schema = "usersDB") // With schema, the DB name is specified
@NoArgsConstructor // Required by JPA
@Setter
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // IDENTITY is required when using H2 embedded databases. Using AUTO or SEQUENCE throws a "HIBERNATE_SEQUENCE" error
    private int id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;
}
