package com.springexamples.multitenancy.repository;

import com.springexamples.multitenancy.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
}
