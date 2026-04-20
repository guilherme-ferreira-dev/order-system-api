package com.gui.order.system.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class OrderResponseDTO {

    private Long id;
    private String description;
    private String status;
    private Long userId;
    private LocalDateTime createdAt;
}