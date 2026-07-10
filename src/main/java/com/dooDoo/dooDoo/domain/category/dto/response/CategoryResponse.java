package com.dooDoo.dooDoo.domain.category.dto.response;

import java.time.LocalDateTime;

public record CategoryResponse(
        Long categoryId,
        String name,
        String color,
        LocalDateTime createdAt
)
{}