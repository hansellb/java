package com.springexamples.springboot.service;

import com.springexamples.springboot.dao.product.ProductRepository;
import com.springexamples.springboot.dao.user.UserRepository;
import com.springexamples.springboot.model.product.Product;
import com.springexamples.springboot.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TestService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void addUser() {
        Integer random = (int) (Math.floor(Math.random() * 10));
        User user = new User();
        user.setName("John Doe " + random);
        user.setEmail("johndon" + random + "@test.com");
        userRepository.save(user);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public void addProduct() {
        Integer random = (int) (Math.floor(Math.random() * 10));
        Product product = new Product();
        product.setName("Book " + random);
        product.setPrice(random);

        productRepository.save(product);
    }
}
