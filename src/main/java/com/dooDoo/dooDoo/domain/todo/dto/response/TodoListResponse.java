package com.dooDoo.dooDoo.domain.todo.dto.response;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public record TodoListResponse(

        LocalDate date,
        DayOfWeek dayOfWeek,
        TodoSummaryResponse summary,
        List<TodoResponse> todos

) {

    public static TodoListResponse of(
            LocalDate date,
            TodoSummaryResponse summary,
            List<TodoResponse> todos
    ) {
        return new TodoListResponse(
                date,
                date.getDayOfWeek(),
                summary,
                todos
        );
    }
}