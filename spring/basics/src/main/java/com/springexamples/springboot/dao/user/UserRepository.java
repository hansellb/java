package com.springexamples.springboot.dao.user;

import com.springexamples.springboot.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
