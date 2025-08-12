package com.jp.orpha.whatsapp_order.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SettingsDto {
    private Long id;

    @NotBlank
    private String storeName;

    @NotBlank
    @Pattern(regexp = "^\\d{9,15}$", message = "adminPhone debe ser numérico, sin +, entre 9 y 15 dígitos (ej: 56912345678)")
    private String adminPhone;

    @NotBlank
    private String currency;
}

