package com.jp.orpha.whatsapp_order.mappers;

import com.jp.orpha.whatsapp_order.dtos.MessageTemplateDto;
import com.jp.orpha.whatsapp_order.entities.MessageTemplate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MessageTemplateMapper {

    MessageTemplateDto toDto(MessageTemplate e);
    @Mapping(target = "createdAt", ignore = true)
    MessageTemplate toEntity(MessageTemplateDto d);
}
