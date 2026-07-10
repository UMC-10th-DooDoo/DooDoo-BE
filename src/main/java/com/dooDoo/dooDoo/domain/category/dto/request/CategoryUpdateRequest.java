package com.dooDoo.dooDoo.domain.category.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CategoryUpdateRequest(
        @NotBlank String name,
        @NotBlank String color
) {}