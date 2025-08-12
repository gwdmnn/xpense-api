package com.xpense.xpensedemo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record IncomeDTO(
    @NotBlank(message = "Description cannot be blank")
    String description,
    
    String category,
    
    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Income amount must be positive")
    double amount,
    
    @NotBlank(message = "Date cannot be blank")
    String date) {
}
