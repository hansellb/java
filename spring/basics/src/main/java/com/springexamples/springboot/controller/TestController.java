package com.springexamples.springboot.controller;

import com.springexamples.springboot.model.product.Product;
import com.springexamples.springboot.model.user.User;
import com.springexamples.springboot.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TestController {

    private TestService testService;

    @Autowired
    public TestController(TestService testService){
        this.testService = testService;
    }

    @GetMapping("/user")
    public List<User> getAllUsers() {
        return testService.getAllUsers();
    }

    @PostMapping("/user")
    public void addUser() {
        testService.addUser();
    }

    @GetMapping("/product")
    public List<Product> getAllProducts() {
        return testService.getAllProducts();
    }

    @PostMapping("/product")
    public void addProduct() {
        testService.addProduct();
    }
}
