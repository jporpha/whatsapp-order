package com.jp.orpha.whatsapp_order.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderDraftResponseDto {
    private Long orderId;
    private String generatedText;
    private String whatsAppLink;
    private BigDecimal total;
    private List<ItemDto> items;
}
