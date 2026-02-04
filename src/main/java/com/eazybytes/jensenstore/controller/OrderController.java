package com.eazybytes.jensenstore.controller;

import com.eazybytes.jensenstore.dto.OrderRequestDto;
import com.eazybytes.jensenstore.dto.OrderResponseDto;
import com.eazybytes.jensenstore.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final IOrderService iOrderService;

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody OrderRequestDto orderRequestDto){
        iOrderService.createOrder(orderRequestDto);
        return ResponseEntity.ok("Order created successfully!");
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> loadCustomerOrders(){
        List<OrderResponseDto> customerOrders = iOrderService.getCustomerOrders();
        return ResponseEntity.ok(customerOrders);
    }

}
