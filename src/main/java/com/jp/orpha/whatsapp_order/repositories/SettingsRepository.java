package com.jp.orpha.whatsapp_order.repositories;

import com.jp.orpha.whatsapp_order.entities.Settings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettingsRepository extends JpaRepository<Settings, Long> {
}
