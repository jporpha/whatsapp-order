package com.jp.orpha.whatsapp_order.dtos;

import com.jp.orpha.whatsapp_order.enums.DeliveryMode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class OrderDraftRequestDto {

    @NotBlank
    private String customerName;

    @NotBlank
    @Pattern(regexp = "^\\d{9,15}$", message = "customerPhone debe ser numérico, sin +, entre 9 y 15 dígitos")
    private String customerPhone;

    private String address;

    @NotNull
    private DeliveryMode deliveryMode;

    @Size(max = 1000)
    private String note;

    @NotNull
    @Size(min = 1, message = "Debe incluir al menos un ítem")
    private List<ItemDto> items;


}

