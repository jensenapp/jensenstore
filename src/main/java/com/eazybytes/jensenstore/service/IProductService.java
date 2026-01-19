package com.eazybytes.jensenstore.service;

import com.eazybytes.jensenstore.dto.ProductDto;

import java.util.List;

public interface IProductService {
    List<ProductDto> getProducts();
}
