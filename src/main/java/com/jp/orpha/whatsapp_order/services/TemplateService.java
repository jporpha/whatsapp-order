package com.jp.orpha.whatsapp_order.services;

import com.jp.orpha.whatsapp_order.dtos.MessageTemplateDto;

import java.util.List;

public interface TemplateService {
    MessageTemplateDto create(MessageTemplateDto dto);
    MessageTemplateDto update(Long id, MessageTemplateDto dto);
    void delete(Long id);
    List<MessageTemplateDto> list();
    MessageTemplateDto getActive();
}
