package com.dooDoo.dooDoo.domain.category.service;

import com.dooDoo.dooDoo.domain.category.dto.converter.CategoryConverter;
import com.dooDoo.dooDoo.domain.category.dto.request.CategoryCreateRequest;
import com.dooDoo.dooDoo.domain.category.dto.request.CategoryUpdateRequest;
import com.dooDoo.dooDoo.domain.category.dto.response.CategoryResponse;
import com.dooDoo.dooDoo.domain.category.entity.Category;
import com.dooDoo.dooDoo.domain.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public CategoryResponse createCategory(Long userId, CategoryCreateRequest request) {
        checkDuplicateName(userId, request.name());

        Category category = Category.builder()
                .userId(userId)
                .name(request.name())
                .color(request.color())
                .build();

        Category saved = categoryRepository.save(category);
        return CategoryConverter.toCategoryResponse(saved);
    }

    public CategoryResponse getCategory(Long userId, Long categoryId) {
        Category category = findCategory(categoryId);
        checkOwner(category, userId);
        return CategoryConverter.toCategoryResponse(category);
    }

    public List<CategoryResponse> getCategories(Long userId) {
        return categoryRepository.findAllByUserId(userId).stream()
                .map(CategoryConverter::toCategoryResponse)
                .toList();
    }

    @Transactional
    public CategoryResponse updateCategory(Long userId, Long categoryId, CategoryUpdateRequest request) {
        Category category = findCategory(categoryId);
        checkOwner(category, userId);

        boolean isNameChanged = !category.getName().equals(request.name());
        if (isNameChanged) {
            checkDuplicateName(userId, request.name());
        }

        category.update(request.name(), request.color());
        return CategoryConverter.toCategoryResponse(category);
    }

    @Transactional
    public void deleteCategory(Long userId, Long categoryId) {
        Category category = findCategory(categoryId);
        checkOwner(category, userId);
        categoryRepository.delete(category);
    }

    private Category findCategory(Long categoryId) {
        return categoryRepository
                .findById(categoryId)
                .orElseThrow(
                        () -> new IllegalArgumentException(
                                "카테고리를 찾을 수 없습니다."
                        )
                );
    }

    private void checkOwner(Category category, Long userId) {
        if (!category.getUserId().equals(userId)) {
            throw new IllegalArgumentException(
                    "본인의 카테고리가 아닙니다."
            );
        }
    }

    private void checkDuplicateName(Long userId, String name) {
        if (categoryRepository.existsByUserIdAndName(userId, name)) {
            throw new IllegalArgumentException(
                    "이미 존재하는 카테고리 이름입니다."
            );
        }
    }
}
