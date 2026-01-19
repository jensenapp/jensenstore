package com.eazybytes.jensenstore.service.impl;

import com.eazybytes.jensenstore.dto.ProductDto;
import com.eazybytes.jensenstore.entity.Product;
import com.eazybytes.jensenstore.repository.ProductRepository;
import com.eazybytes.jensenstore.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class IProductServiceImpl implements IProductService {

private final ProductRepository productRepository;

    @Override
    public List<ProductDto> getProducts() {
        List<ProductDto> collect = productRepository.findAll()
                .stream()
                .map(this::transformToDTO)
                .collect(Collectors.toList());
        return collect;
    }

    private ProductDto transformToDTO(Product product){
        ProductDto productDto = new ProductDto();
        BeanUtils.copyProperties(product,productDto);
        productDto.setProductId(product.getId());
        return productDto;
    }
}
