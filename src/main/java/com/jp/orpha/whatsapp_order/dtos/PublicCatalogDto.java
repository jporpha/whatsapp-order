package com.jp.orpha.whatsapp_order.dtos;

import lombok.Data;

import java.util.List;

@Data
public class PublicCatalogDto {
    private String storeName;
    private String currency;
    private List<ProductItemDto> products;


}
