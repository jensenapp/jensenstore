package com.eazybytes.jensenstore.repository;

import com.eazybytes.jensenstore.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
