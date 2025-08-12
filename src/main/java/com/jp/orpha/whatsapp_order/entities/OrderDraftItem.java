package com.jp.orpha.whatsapp_order.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "order_draft_items")
@Data
@NoArgsConstructor
public class OrderDraftItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional=false)
    @JoinColumn(name = "order_draft")
    private OrderDraft orderDraft;

    @Column(name = "product_id", nullable=false)
    private Long productId;

    @Column(name = "product_name_snapshot", nullable=false)
    private String productNameSnapshot;

    @Column(name = "unit_price_snapshot", nullable=false, precision = 12, scale = 2)
    private BigDecimal unitPriceSnapshot;

    @Column(name = "quantity", nullable=false)
    private Integer quantity;

    @Column(name = "subtotal", nullable=false, precision = 12, scale = 2)
    private BigDecimal subtotal;
}