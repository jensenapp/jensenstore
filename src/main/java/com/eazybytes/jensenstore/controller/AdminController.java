package com.eazybytes.jensenstore.controller;

import com.eazybytes.jensenstore.constants.ApplicationConstants;
import com.eazybytes.jensenstore.dto.ContactResponseDto;
import com.eazybytes.jensenstore.dto.OrderResponseDto;
import com.eazybytes.jensenstore.dto.ResponseDto;
import com.eazybytes.jensenstore.entity.Order;
import com.eazybytes.jensenstore.service.IContactService;
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

    private final IContactService iContactService;

    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponseDto>>getAllPendingOrders(){
        List<OrderResponseDto> allPendingOrders = orderService.getAllPendingOrders();
        return ResponseEntity.ok().body(allPendingOrders);
    }
    @PatchMapping("/orders/{orderId}/confirm")
    public ResponseEntity<ResponseDto> confirmOrder(@PathVariable Long orderId){
       orderService.updateOrder(orderId, ApplicationConstants.ORDER_STATUS_CONFIRMED);
       return ResponseEntity.ok(new ResponseDto("200", "Order #" + orderId + " has been approved."));
    }

    @PatchMapping("/orders/{orderId}/cancel")
    public ResponseEntity<ResponseDto> cancelOrder(@PathVariable Long orderId){
       orderService.updateOrder(orderId,ApplicationConstants.ORDER_STATUS_CANCELLED);
        return ResponseEntity.ok(new ResponseDto("200", "Order #" +orderId + " has been cancelled."));
    }

    @GetMapping("/messages")
    public ResponseEntity<List<ContactResponseDto>> getAllOpenMessages(){
        List<ContactResponseDto> allOpenMessages = iContactService.getAllOpenMessages();
        return ResponseEntity.ok(allOpenMessages);
    }

    @PatchMapping("/messages/{contactId}/close")
    public ResponseEntity<ResponseDto> closeMessage(@PathVariable Long contactId){
        iContactService.updateMessageStatus(contactId, ApplicationConstants.CLOSED_MESSAGE);
        return ResponseEntity.ok(new ResponseDto("200","Contact #" + contactId + " has been closed."));
    }
}
