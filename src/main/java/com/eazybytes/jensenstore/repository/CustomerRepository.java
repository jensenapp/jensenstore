package com.eazybytes.jensenstore.repository;

import com.eazybytes.jensenstore.entity.Customer;
import com.eazybytes.jensenstore.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {

    Optional<Customer> findByEmail(String email);

    Optional<Customer> findByEmailOrMobileNumber(String email,String mobileNumber);
}
