package com.jp.orpha.whatsapp_order.controllers;

import com.jp.orpha.whatsapp_order.dtos.OrderDraftRequestDto;
import com.jp.orpha.whatsapp_order.dtos.OrderDraftResponseDto;
import com.jp.orpha.whatsapp_order.dtos.PublicCatalogDto;
import com.jp.orpha.whatsapp_order.services.OrderDraftService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/public")
@Data
public class PublicController {

    private final OrderDraftService service;

    @Operation(summary = "Obtener catálogo público (productos disponibles)")
    @GetMapping("/catalog")
    public ResponseEntity<PublicCatalogDto> catalog() {
        return ResponseEntity.ok(service.getPublicCatalog());
    }

    @Operation(summary = "Generar pedido y deeplink de WhatsApp")
    @PostMapping("/order/generate")
    public ResponseEntity<OrderDraftResponseDto> generate(@Valid @RequestBody OrderDraftRequestDto req) {
        return ResponseEntity.ok(service.generateOrder(req));
    }
}