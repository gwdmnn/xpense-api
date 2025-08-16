package com.xpense.xpensedemo.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoryDTO(
    @NotBlank(message = "Category name cannot be blank")
    String name
) {
}
