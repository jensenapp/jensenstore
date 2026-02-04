package com.eazybytes.jensenstore.repository;

import com.eazybytes.jensenstore.dto.OrderResponseDto;
import com.eazybytes.jensenstore.entity.Customer;
import com.eazybytes.jensenstore.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {

    List<Order> findByCustomerOrderByCreatedAtDesc(Customer customer);

    List<Order> findByOrOrderStatus(String orderStatus);
}
