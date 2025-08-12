package com.jp.orpha.whatsapp_order.services;

import com.jp.orpha.whatsapp_order.dtos.OrderDraftRequestDto;
import com.jp.orpha.whatsapp_order.dtos.OrderDraftResponseDto;
import com.jp.orpha.whatsapp_order.dtos.PublicCatalogDto;

public interface OrderDraftService {
    PublicCatalogDto getPublicCatalog();
    OrderDraftResponseDto generateOrder(OrderDraftRequestDto request);
}
