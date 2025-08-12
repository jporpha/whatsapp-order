package com.jp.orpha.whatsapp_order.services.impl;

import com.jp.orpha.whatsapp_order.dtos.SettingsDto;
import com.jp.orpha.whatsapp_order.entities.Settings;
import com.jp.orpha.whatsapp_order.mappers.SettingsMapper;
import com.jp.orpha.whatsapp_order.repositories.SettingsRepository;
import com.jp.orpha.whatsapp_order.services.SettingsService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class SettingsServiceImpl implements SettingsService {

    private final SettingsRepository repo;
    private final SettingsMapper mapper;

    @Override
    public SettingsDto getOrCreateDefault() {
        return repo.findAll().stream().findFirst()
                .map(mapper::toDto)
                .orElseGet(() -> mapper.toDto(repo.save(defaultSettings())));
    }

    @Override
    public SettingsDto update(SettingsDto dto) {
        Settings current = repo.findAll().stream().findFirst()
                .orElseGet(() -> repo.save(mapper.toEntity(dto)));
        current.setStoreName(dto.getStoreName());
        current.setAdminPhone(dto.getAdminPhone());
        current.setCurrency(dto.getCurrency());
        return mapper.toDto(repo.save(current));
    }

    private Settings defaultSettings() {
        Settings s = new Settings();
        s.setStoreName("Mi Tienda");
        s.setAdminPhone("56912345678");
        s.setCurrency("CLP");
        return s;
    }
}