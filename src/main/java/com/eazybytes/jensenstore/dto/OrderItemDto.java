package com.eazybytes.jensenstore.dto;

import java.math.BigDecimal;

public record OrderItemDto(Long productId,
                           Integer quantity,
                           BigDecimal price) {
}
