package com.dooDoo.dooDoo.domain.todo.controller;

import com.dooDoo.dooDoo.domain.todo.dto.request.TodoCompletionRequest;
import com.dooDoo.dooDoo.domain.todo.dto.request.TodoCreateRequest;
import com.dooDoo.dooDoo.domain.todo.dto.request.TodoUpdateRequest;
import com.dooDoo.dooDoo.domain.todo.dto.response.TodoListResponse;
import com.dooDoo.dooDoo.domain.todo.dto.response.TodoResponse;
import com.dooDoo.dooDoo.domain.todo.service.TodoService;
import jakarta.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<TodoResponse> createTodo(
            @RequestHeader("X-USER-ID") Long userId,
            @Valid @RequestBody TodoCreateRequest request
    ) {
        TodoResponse response =
                todoService.createTodo(userId, request);

        URI location =
                URI.create("/api/todos/" + response.todoId());

        return ResponseEntity
                .created(location)
                .body(response);
    }

    @GetMapping("/{todoId}")
    public ResponseEntity<TodoResponse> getTodo(
            @RequestHeader("X-USER-ID") Long userId,
            @PathVariable Long todoId
    ) {
        TodoResponse response =
                todoService.getTodo(userId, todoId);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<TodoListResponse> getTodosByDate(
            @RequestHeader("X-USER-ID") Long userId,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date
    ) {
        TodoListResponse response =
                todoService.getTodosByDate(userId, date);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/period")
    public ResponseEntity<List<TodoResponse>> getTodosByPeriod(
            @RequestHeader("X-USER-ID") Long userId,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate startDate,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate endDate
    ) {
        List<TodoResponse> response =
                todoService.getTodosByPeriod(
                        userId,
                        startDate,
                        endDate
                );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{todoId}")
    public ResponseEntity<TodoResponse> updateTodo(
            @RequestHeader("X-USER-ID") Long userId,
            @PathVariable Long todoId,
            @Valid @RequestBody TodoUpdateRequest request
    ) {
        TodoResponse response =
                todoService.updateTodo(
                        userId,
                        todoId,
                        request
                );

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{todoId}/completion")
    public ResponseEntity<TodoResponse> updateCompletion(
            @RequestHeader("X-USER-ID") Long userId,
            @PathVariable Long todoId,
            @Valid @RequestBody TodoCompletionRequest request
    ) {
        TodoResponse response =
                todoService.updateCompletion(
                        userId,
                        todoId,
                        request
                );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{todoId}")
    public ResponseEntity<Void> deleteTodo(
            @RequestHeader("X-USER-ID") Long userId,
            @PathVariable Long todoId
    ) {
        todoService.deleteTodo(userId, todoId);

        return ResponseEntity.noContent().build();
    }
}