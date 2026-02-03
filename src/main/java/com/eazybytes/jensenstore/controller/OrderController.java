package com.eazybytes.jensenstore.controller;

import com.eazybytes.jensenstore.dto.OrderRequestDto;
import com.eazybytes.jensenstore.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final IOrderService iOrderService;

    @PostMapping
    public ResponseEntity<String> getOrder(@RequestBody OrderRequestDto orderRequestDto){
        iOrderService.createOrder(orderRequestDto);
        return ResponseEntity.ok("Order created successfully!");
    }
}
