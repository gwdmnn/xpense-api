package com.xpense.xpensedemo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OutputDTO(
    @NotBlank(message = "Description cannot be blank")
    String description,
    
    String category,
    
    @NotNull(message = "Amount cannot be null")
    double amount,
    
    @NotBlank(message = "Date cannot be blank")
    String date
) {
}
