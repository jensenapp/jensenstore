package com.eazybytes.jensenstore.service.impl;

import com.eazybytes.jensenstore.constants.ApplicationConstants;
import com.eazybytes.jensenstore.dto.OrderItemResponseDto;
import com.eazybytes.jensenstore.dto.OrderRequestDto;
import com.eazybytes.jensenstore.dto.OrderResponseDto;
import com.eazybytes.jensenstore.entity.Customer;
import com.eazybytes.jensenstore.entity.Order;
import com.eazybytes.jensenstore.entity.OrderItem;
import com.eazybytes.jensenstore.entity.Product;
import com.eazybytes.jensenstore.exception.ResourceNotFoundException;
import com.eazybytes.jensenstore.repository.OrderRepository;
import com.eazybytes.jensenstore.repository.ProductRepository;
import com.eazybytes.jensenstore.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements IOrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ProfileServiceImpl profileService;


    @Override
    public void createOrder(OrderRequestDto orderRequest) {
        Customer customer = profileService.getAuthenticatedCustomer();
        Order order = new Order();
        order.setCustomer(customer);
        BeanUtils.copyProperties(orderRequest, order);
        order.setOrderStatus(ApplicationConstants.ORDER_STATUS_CREATED);

        List<OrderItem> orderItems = orderRequest.items().stream().map(item -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);

            Product product = productRepository
                    .findById(item.productId()).orElseThrow(() -> new ResourceNotFoundException(
                            "Product",
                            "ProductID",
                            item.productId().toString()
                    ));
            orderItem.setProduct(product);
            orderItem.setPrice(item.price());
            orderItem.setQuantity(item.quantity());
            return orderItem;
        }).toList();

        order.setOrderItems(orderItems);
        orderRepository.save(order);
    }

    @Override
    public List<OrderResponseDto> getCustomerOrders() {

        Customer customer = profileService.getAuthenticatedCustomer();

        List<Order> orders = orderRepository.findOrdersByCustomerWithNativeQuery(customer.getCustomerId());

        List<OrderResponseDto> orderResponseDtoList = orders.stream().map(this::mapToOrderResponseDto).toList();

        return orderResponseDtoList;
    }

    @Override
    public List<OrderResponseDto> getAllPendingOrders() {
        List<Order> orders = orderRepository.findOrdersByStatusWithNativeQuery(ApplicationConstants.ORDER_STATUS_CREATED);
        List<OrderResponseDto> responseDtoList = orders.stream().map(this::mapToOrderResponseDto).toList();
        return responseDtoList;
    }

    @Override
    public void updateOrder(Long orderId, String orderStatus) {

//        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order", "OrderID", orderId.toString()));
//        order.setOrderStatus(orderStatus);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        orderRepository.updateOrder(orderId,orderStatus,email);
    }

    private OrderResponseDto mapToOrderResponseDto(Order order){

        List<OrderItemResponseDto> orderItemReponseDtoList = order.getOrderItems().stream().map(this::mapToOrderItemReponseDto).toList();

        OrderResponseDto orderResponseDto =
                new OrderResponseDto(
                        order.getOrderId(),
                        order.getOrderStatus(),
                        order.getTotalPrice(),
                        order.getCreatedAt().toString(),
                        orderItemReponseDtoList);
        return orderResponseDto;
    }

    private OrderItemResponseDto mapToOrderItemReponseDto(OrderItem orderItem){
        OrderItemResponseDto orderItemReponseDto =
                new OrderItemResponseDto(
                        orderItem.getProduct().getName(),
                        orderItem.getQuantity(),
                        orderItem.getPrice(),
                        orderItem.getProduct().getImageUrl());
        return orderItemReponseDto;
    }
}
