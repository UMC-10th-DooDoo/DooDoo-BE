package com.dooDoo.dooDoo.domain.todo.dto.response;

public record TodoSummaryResponse(

        long totalCount,
        long completedCount,
        long incompleteCount

) {

    public static TodoSummaryResponse of(
            long totalCount,
            long completedCount
    ) {
        return new TodoSummaryResponse(
                totalCount,
                completedCount,
                totalCount - completedCount
        );
    }
}