package com.xpense.xpensedemo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;

@Validated
public record OutputDTO(
    @NotBlank(message = "Description cannot be blank")
    @NotNull(message = "Description cannot be null")
    String description,

    @NotBlank(message = "Category cannot be blank")
    @NotNull(message = "Category cannot be null")
    String category,
    
    @NotNull(message = "Amount cannot be null")
    @NotBlank(message = "Amount cannot be blank")
    @Positive(message = "Output amount must be positive")
    double amount,
    
    @NotBlank(message = "Date cannot be blank")
    @NotNull(message = "Date cannot be null")
    String date
) {
}
