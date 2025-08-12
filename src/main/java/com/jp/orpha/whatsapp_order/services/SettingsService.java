package com.jp.orpha.whatsapp_order.services;

import com.jp.orpha.whatsapp_order.dtos.SettingsDto;

public interface SettingsService {
    SettingsDto getOrCreateDefault();
    SettingsDto update(SettingsDto dto);
}
