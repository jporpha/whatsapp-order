package com.jp.orpha.whatsapp_order.entities;

import com.jp.orpha.whatsapp_order.enums.DeliveryMode;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "order_drafts")
@Data
@NoArgsConstructor
public class OrderDraft {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_name", nullable=false)
    private String customerName;
    @Column(name = "customer_phone", nullable=false)
    private String customerPhone;
    @Column(name = "address")
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_mode", nullable=false)
    private DeliveryMode deliveryMode;

    @Column(name = "note")
    private String note;

    @Column(name = "total", nullable=false, precision = 12, scale = 2)
    private BigDecimal total;

    @Column(name = "generated_text", nullable=false, length = 4000)
    private String generatedText;

    @Column(name = "whats_app_link", length = 2000)
    private String whatsAppLink;

    @Column(name = "created_at", nullable=false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "orderDraft", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderDraftItem> items;

    @PrePersist
    void prePersist() { createdAt = LocalDateTime.now(); }
}
