package com.eazybytes.jensenstore.repository;

import com.eazybytes.jensenstore.entity.Customer;
import com.eazybytes.jensenstore.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {
}
