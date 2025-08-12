package com.jp.orpha.whatsapp_order.repositories;

import com.jp.orpha.whatsapp_order.entities.MessageTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MessageTemplateRepository extends JpaRepository<MessageTemplate, Long> {
    Optional<MessageTemplate> findFirstByActiveTrueOrderByIdAsc();
}
