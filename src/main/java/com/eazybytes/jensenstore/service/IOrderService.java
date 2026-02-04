package com.eazybytes.jensenstore.service;

import com.eazybytes.jensenstore.dto.OrderRequestDto;
import com.eazybytes.jensenstore.dto.OrderResponseDto;
import com.eazybytes.jensenstore.entity.Order;

import java.util.List;

public interface IOrderService {
    void createOrder(OrderRequestDto orderRequestDto);
    List<OrderResponseDto> getCustomerOrders();
    List<OrderResponseDto> getAllPendingOrders();
    Order updateOrder(Long orderId,String orderStatus);
}
