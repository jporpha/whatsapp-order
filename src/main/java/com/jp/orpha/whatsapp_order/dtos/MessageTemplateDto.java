package com.jp.orpha.whatsapp_order.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MessageTemplateDto {
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    @Size(max = 4000)
    private String templateText;

    @NotNull
    private Boolean active;
}

