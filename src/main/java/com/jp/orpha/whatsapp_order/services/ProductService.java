package com.jp.orpha.whatsapp_order.services;

import com.jp.orpha.whatsapp_order.dtos.ProductDto;

import java.util.List;

public interface ProductService {
    ProductDto create(ProductDto dto);
    ProductDto update(Long id, ProductDto dto);
    void delete(Long id);
    ProductDto get(Long id);
    List<ProductDto> list(boolean onlyAvailable);
}
