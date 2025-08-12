package com.jp.orpha.whatsapp_order.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemDto {
    @NotNull
    private Long productId;

    @NotNull @Positive
    private Integer quantity;
    private String productName;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;
}
