package com.dooDoo.dooDoo.domain.category.repository;

import com.dooDoo.dooDoo.domain.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository
        extends JpaRepository<Category, Long> {
}