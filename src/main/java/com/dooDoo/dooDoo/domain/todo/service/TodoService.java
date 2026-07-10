package com.dooDoo.dooDoo.domain.todo.service;

import com.dooDoo.dooDoo.domain.category.entity.Category;
import com.dooDoo.dooDoo.domain.category.repository.CategoryRepository;
import com.dooDoo.dooDoo.domain.todo.dto.request.TodoCompletionRequest;
import com.dooDoo.dooDoo.domain.todo.dto.request.TodoCreateRequest;
import com.dooDoo.dooDoo.domain.todo.dto.request.TodoUpdateRequest;
import com.dooDoo.dooDoo.domain.todo.dto.response.TodoListResponse;
import com.dooDoo.dooDoo.domain.todo.dto.response.TodoResponse;
import com.dooDoo.dooDoo.domain.todo.dto.response.TodoSummaryResponse;
import com.dooDoo.dooDoo.domain.todo.entity.Todo;
import com.dooDoo.dooDoo.domain.todo.repository.TodoRepository;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoService {

    private final TodoRepository todoRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public TodoResponse createTodo(
            Long userId,
            TodoCreateRequest request
    ) {
        Category category = findCategory(request.categoryId());

        Todo todo = Todo.builder()
                .userId(userId)
                .category(category)
                .content(request.content())
                .date(request.date())
                .priority(request.priority())
                .build();

        Todo savedTodo = todoRepository.save(todo);

        return TodoResponse.from(savedTodo);
    }

    public TodoResponse getTodo(
            Long userId,
            Long todoId
    ) {
        Todo todo = findTodo(userId, todoId);

        return TodoResponse.from(todo);
    }

    public TodoListResponse getTodosByDate(
            Long userId,
            LocalDate date
    ) {
        List<Todo> todos =
                todoRepository
                        .findAllByUserIdAndDateAndDeletedAtIsNullOrderByPriorityAscIdAsc(
                                userId,
                                date
                        );

        List<TodoResponse> todoResponses = todos.stream()
                .map(TodoResponse::from)
                .toList();

        long totalCount = todos.size();

        long completedCount = todos.stream()
                .filter(Todo::isCompleted)
                .count();

        TodoSummaryResponse summary =
                TodoSummaryResponse.of(
                        totalCount,
                        completedCount
                );

        return TodoListResponse.of(
                date,
                summary,
                todoResponses
        );
    }

    public List<TodoResponse> getTodosByPeriod(
            Long userId,
            LocalDate startDate,
            LocalDate endDate
    ) {
        validateDateRange(startDate, endDate);

        return todoRepository
                .findAllByUserIdAndDateBetweenAndDeletedAtIsNullOrderByDateAscPriorityAscIdAsc(
                        userId,
                        startDate,
                        endDate
                )
                .stream()
                .map(TodoResponse::from)
                .toList();
    }

    @Transactional
    public TodoResponse updateTodo(
            Long userId,
            Long todoId,
            TodoUpdateRequest request
    ) {
        Todo todo = findTodo(userId, todoId);
        Category category = findCategory(request.categoryId());

        todo.update(
                category,
                request.content(),
                request.date(),
                request.priority()
        );

        return TodoResponse.from(todo);
    }

    @Transactional
    public TodoResponse updateCompletion(
            Long userId,
            Long todoId,
            TodoCompletionRequest request
    ) {
        Todo todo = findTodo(userId, todoId);

        todo.updateCompletion(request.completed());

        return TodoResponse.from(todo);
    }

    @Transactional
    public void deleteTodo(
            Long userId,
            Long todoId
    ) {
        Todo todo = findTodo(userId, todoId);

        todo.delete();
    }

    private Todo findTodo(
            Long userId,
            Long todoId
    ) {
        return todoRepository
                .findByIdAndUserIdAndDeletedAtIsNull(
                        todoId,
                        userId
                )
                .orElseThrow(
                        () -> new IllegalArgumentException(
                                "할 일을 찾을 수 없습니다."
                        )
                );
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

    private void validateDateRange(
            LocalDate startDate,
            LocalDate endDate
    ) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException(
                    "시작일과 종료일은 필수입니다."
            );
        }

        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException(
                    "시작일은 종료일보다 늦을 수 없습니다."
            );
        }
    }
}