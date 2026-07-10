package com.dooDoo.dooDoo.domain.todo.dto.request;

import jakarta.validation.constraints.NotNull;

public record TodoCompletionRequest(

        @NotNull
        Boolean completed

) {
}