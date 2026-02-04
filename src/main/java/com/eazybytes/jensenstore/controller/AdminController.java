package com.eazybytes.jensenstore.controller;

import com.eazybytes.jensenstore.constants.ApplicationConstants;
import com.eazybytes.jensenstore.dto.OrderResponseDto;
import com.eazybytes.jensenstore.dto.ResponseDto;
import com.eazybytes.jensenstore.entity.Order;
import com.eazybytes.jensenstore.service.IOrderService;
import com.eazybytes.jensenstore.service.impl.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/admin/")
public class AdminController {

    private final IOrderService orderService;

    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponseDto>>getAllPendingOrders(){
        List<OrderResponseDto> allPendingOrders = orderService.getAllPendingOrders();
        return ResponseEntity.ok().body(allPendingOrders);
    }
    @PatchMapping("/orders/{orderId}/confirm")
    public ResponseEntity<ResponseDto> confirmOrder(@PathVariable Long orderId){
        Order confirmedOrder = orderService.updateOrder(orderId, ApplicationConstants.ORDER_STATUS_CONFIRMED);
       return ResponseEntity.ok(new ResponseDto("200", "Order #" + confirmedOrder.getOrderId() + " has been approved."));
    }

    @PatchMapping("/orders/{orderId}/cancel")
    public ResponseEntity<ResponseDto> cancelOrder(@PathVariable Long orderId){
        Order cancelledOrder = orderService.updateOrder(orderId,ApplicationConstants.ORDER_STATUS_CANCELLED);
        return ResponseEntity.ok(new ResponseDto("200", "Order #" + cancelledOrder.getOrderId() + " has been cancelled."));
    }
}
