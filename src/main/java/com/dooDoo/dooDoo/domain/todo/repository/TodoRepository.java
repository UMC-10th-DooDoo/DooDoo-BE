package com.dooDoo.dooDoo.domain.todo.repository;

import com.dooDoo.dooDoo.domain.todo.entity.Todo;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    Optional<Todo> findByIdAndUserIdAndDeletedAtIsNull(
            Long todoId,
            Long userId
    );

    List<Todo> findAllByUserIdAndDateAndDeletedAtIsNullOrderByPriorityAscIdAsc(
            Long userId,
            LocalDate date
    );

    List<Todo> findAllByUserIdAndDateBetweenAndDeletedAtIsNullOrderByDateAscPriorityAscIdAsc(
            Long userId,
            LocalDate startDate,
            LocalDate endDate
    );
}