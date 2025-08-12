package com.jp.orpha.whatsapp_order.controllers;

import com.jp.orpha.whatsapp_order.dtos.MessageTemplateDto;
import com.jp.orpha.whatsapp_order.dtos.SettingsDto;
import com.jp.orpha.whatsapp_order.services.SettingsService;
import com.jp.orpha.whatsapp_order.services.TemplateService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@Data
public class AdminController {

    private final SettingsService settingsService;
    private final TemplateService templateService;

    @Operation(summary = "Obtener o crear configuración por defecto")
    @GetMapping("/settings")
    public ResponseEntity<SettingsDto> getSettings() {
        return ResponseEntity.ok(settingsService.getOrCreateDefault());
    }

    @Operation(summary = "Actualizar configuración")
    @PutMapping("/settings")
    public ResponseEntity<SettingsDto> updateSettings(@Valid @RequestBody SettingsDto dto) {
        return ResponseEntity.ok(settingsService.update(dto));
    }

    @Operation(summary = "Crear plantilla de mensaje")
    @PostMapping("/templates")
    public ResponseEntity<MessageTemplateDto> createTemplate(@Valid @RequestBody MessageTemplateDto dto) {
        return ResponseEntity.ok(templateService.create(dto));
    }

    @Operation(summary = "Actualizar plantilla de mensaje")
    @PutMapping("/templates/{id}")
    public ResponseEntity<MessageTemplateDto> updateTemplate(@PathVariable Long id, @Valid @RequestBody MessageTemplateDto dto) {
        return ResponseEntity.ok(templateService.update(id, dto));
    }

    @Operation(summary = "Eliminar plantilla de mensaje")
    @DeleteMapping("/templates/{id}")
    public ResponseEntity<Void> deleteTemplate(@PathVariable Long id) {
        templateService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Listar plantillas")
    @GetMapping("/templates")
    public ResponseEntity<List<MessageTemplateDto>> listTemplates() {
        return ResponseEntity.ok(templateService.list());
    }

    @Operation(summary = "Obtener plantilla activa")
    @GetMapping("/templates/active")
    public ResponseEntity<MessageTemplateDto> activeTemplate() {
        return ResponseEntity.ok(templateService.getActive());
    }
}
