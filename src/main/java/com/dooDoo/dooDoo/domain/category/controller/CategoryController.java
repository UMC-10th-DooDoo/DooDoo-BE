package com.dooDoo.dooDoo.domain.category.controller;

import com.dooDoo.dooDoo.domain.category.dto.request.CategoryCreateRequest;
import com.dooDoo.dooDoo.domain.category.dto.request.CategoryUpdateRequest;
import com.dooDoo.dooDoo.domain.category.dto.response.CategoryResponse;
import com.dooDoo.dooDoo.domain.category.service.CategoryService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(
            HttpSession session,
            @Valid @RequestBody CategoryCreateRequest request
    ) {
        CategoryResponse response =
                categoryService.createCategory(getUserId(session), request);

        URI location =
                URI.create("/api/categories/" + response.categoryId());

        return ResponseEntity
                .created(location)
                .body(response);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryResponse> getCategory(
            HttpSession session,
            @PathVariable Long categoryId
    ) {
        CategoryResponse response =
                categoryService.getCategory(getUserId(session), categoryId);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getCategories(
            HttpSession session
    ) {
        List<CategoryResponse> response =
                categoryService.getCategories(getUserId(session));

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryResponse> updateCategory(
            HttpSession session,
            @PathVariable Long categoryId,
            @Valid @RequestBody CategoryUpdateRequest request
    ) {
        CategoryResponse response =
                categoryService.updateCategory(
                        getUserId(session),
                        categoryId,
                        request
                );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(
            HttpSession session,
            @PathVariable Long categoryId
    ) {
        categoryService.deleteCategory(getUserId(session), categoryId);

        return ResponseEntity.noContent().build();
    }

    private Long getUserId(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "로그인이 필요합니다."
            );
        }

        return userId;
    }
}
