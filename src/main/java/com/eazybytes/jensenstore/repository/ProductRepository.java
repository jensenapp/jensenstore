package com.eazybytes.jensenstore.repository;

import com.eazybytes.jensenstore.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
