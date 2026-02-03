package com.eazybytes.jensenstore.service.impl;

import com.eazybytes.jensenstore.constants.ApplicationConstants;
import com.eazybytes.jensenstore.dto.OrderRequestDto;
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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
}
