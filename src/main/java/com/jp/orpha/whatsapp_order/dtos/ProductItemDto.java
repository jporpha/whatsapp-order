package com.jp.orpha.whatsapp_order.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductItemDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private boolean available;
    private Integer position;
}
