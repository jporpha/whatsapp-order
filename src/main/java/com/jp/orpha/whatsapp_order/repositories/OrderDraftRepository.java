package com.jp.orpha.whatsapp_order.repositories;

import com.jp.orpha.whatsapp_order.entities.OrderDraft;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDraftRepository extends JpaRepository<OrderDraft, Long> {
}