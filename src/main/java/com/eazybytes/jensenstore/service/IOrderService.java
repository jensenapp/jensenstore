package com.eazybytes.jensenstore.service;

import com.eazybytes.jensenstore.dto.OrderRequestDto;

public interface IOrderService {
    void createOrder(OrderRequestDto orderRequestDto);
}
