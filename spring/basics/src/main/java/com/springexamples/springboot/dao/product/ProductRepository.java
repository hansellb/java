package com.springexamples.springboot.dao.product;

import com.springexamples.springboot.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
