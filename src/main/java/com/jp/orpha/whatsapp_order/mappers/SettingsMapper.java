package com.jp.orpha.whatsapp_order.mappers;

import com.jp.orpha.whatsapp_order.dtos.SettingsDto;
import com.jp.orpha.whatsapp_order.entities.Settings;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SettingsMapper {

    SettingsDto toDto(Settings e);
    @Mapping(target = "createdAt", ignore = true)
    Settings toEntity(SettingsDto d);
}
