package com.dooDoo.dooDoo.domain.category.dto.converter;

import com.dooDoo.dooDoo.domain.category.dto.response.CategoryResponse;
import com.dooDoo.dooDoo.domain.category.entity.Category;

public class CategoryConverter {
    public static CategoryResponse toCategoryResponse(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getColor(),
                category.getCreatedAt()
        );
    }
}
