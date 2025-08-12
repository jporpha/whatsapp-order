package com.jp.orpha.whatsapp_order.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable=false)
    private String name;

    @Column(name = "description", length=1000)
    private String description;

    @Column(name = "price", nullable=false, precision = 12, scale = 2)
    private BigDecimal price;

    @Column(name = "currency", nullable=false, length=3)
    private String currency; // CLP/CLF/USD (en MVP: CLP por defecto)

    @Column(name = "available", nullable=false)
    private boolean available;

    @Column(name = "position", nullable=false)
    private Integer position;

    @Column(name = "created_at", nullable=false)
    private LocalDateTime createdAt;

    @PrePersist
    void prePersist() { createdAt = LocalDateTime.now(); if(currency==null) currency="CLP"; }
}
