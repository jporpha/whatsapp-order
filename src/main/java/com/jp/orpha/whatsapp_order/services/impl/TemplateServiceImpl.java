package com.jp.orpha.whatsapp_order.services.impl;

import com.jp.orpha.whatsapp_order.dtos.MessageTemplateDto;
import com.jp.orpha.whatsapp_order.entities.MessageTemplate;
import com.jp.orpha.whatsapp_order.exceptions.NotFoundException;
import com.jp.orpha.whatsapp_order.mappers.MessageTemplateMapper;
import com.jp.orpha.whatsapp_order.repositories.MessageTemplateRepository;
import com.jp.orpha.whatsapp_order.services.TemplateService;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TemplateServiceImpl implements TemplateService {

    private final MessageTemplateRepository repo;
    private final MessageTemplateMapper mapper;

    @Override
    public MessageTemplateDto create(MessageTemplateDto dto) {
        MessageTemplate e = mapper.toEntity(dto);
        e.setId(null);
        if (Boolean.TRUE.equals(dto.getActive())) {
            // Desactivar otras
            repo.findAll().forEach(t -> { t.setActive(false); repo.save(t); });
        }
        return mapper.toDto(repo.save(e));
    }

    @Override
    public MessageTemplateDto update(Long id, MessageTemplateDto dto) {
        MessageTemplate e = repo.findById(id).orElseThrow(() -> new NotFoundException("Plantilla no encontrada"));
        e.setName(dto.getName());
        e.setTemplateText(dto.getTemplateText());
        if (Boolean.TRUE.equals(dto.getActive())) {
            repo.findAll().forEach(t -> { t.setActive(false); repo.save(t); });
            e.setActive(true);
        } else if (dto.getActive() != null) {
            e.setActive(false);
        }
        return mapper.toDto(repo.save(e));
    }

    @Override
    public void delete(Long id) { repo.deleteById(id); }

    @Override
    @Transactional(readOnly = true)
    public List<MessageTemplateDto> list() {
        return repo.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public MessageTemplateDto getActive() {
        return repo.findFirstByActiveTrueOrderByIdAsc().map(mapper::toDto).orElse(null);
    }
}

