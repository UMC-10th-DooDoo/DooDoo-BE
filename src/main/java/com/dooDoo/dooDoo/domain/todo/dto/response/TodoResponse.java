package com.dooDoo.dooDoo.domain.todo.dto.response;

import com.dooDoo.dooDoo.domain.todo.entity.Todo;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record TodoResponse(

        Long todoId,
        String content,
        Long categoryId,
        String categoryName,
        int priority,
        LocalDate date,
        boolean completed,
        LocalDateTime completedAt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {

    public static TodoResponse from(Todo todo) {
        return new TodoResponse(
                todo.getId(),
                todo.getContent(),
                todo.getCategory().getId(),
                todo.getCategory().getName(),
                todo.getPriority(),
                todo.getDate(),
                todo.isCompleted(),
                todo.getCompletedAt(),
                todo.getCreatedAt(),
                todo.getUpdatedAt()
        );
    }
}
