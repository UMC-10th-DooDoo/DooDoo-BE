package com.dooDoo.dooDoo.domain.todo.dto.response;

import com.dooDoo.dooDoo.domain.todo.entity.Todo;
import com.dooDoo.dooDoo.domain.todo.entity.TodoCategory;
import com.dooDoo.dooDoo.domain.todo.entity.TodoPriority;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record TodoResponse(

        Long todoId,
        String title,
        TodoCategory category,
        String categoryName,
        TodoPriority priority,
        int priorityValue,
        LocalDate todoDate,
        boolean completed,
        LocalDateTime completedAt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {

    public static TodoResponse from(Todo todo) {
        return new TodoResponse(
                todo.getId(),
                todo.get(),
                todo.getCategory(),
                todo.getCategory().getDisplayName(),
                todo.getPriority(),
                todo.getPriority().getValue(),
                todo.getTodoDate(),
                todo.isCompleted(),
                todo.getCompletedAt(),
                todo.getCreatedAt(),
                todo.getUpdatedAt()
        );
    }
}