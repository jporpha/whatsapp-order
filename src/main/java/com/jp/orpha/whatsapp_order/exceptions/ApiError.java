package com.jp.orpha.whatsapp_order.exceptions;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApiError {
    private LocalDateTime timestamp;
    private String path;
    private String message;
    private String code;
}
