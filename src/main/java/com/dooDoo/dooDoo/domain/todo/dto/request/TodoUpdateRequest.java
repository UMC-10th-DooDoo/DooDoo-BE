package com.dooDoo.dooDoo.domain.todo.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record TodoUpdateRequest(

        @NotNull(message = "카테고리는 필수입니다.")
        Long categoryId,

        @NotBlank(message = "할 일 내용을 입력해주세요.")
        String content,

        @NotNull(message = "날짜는 필수입니다.")
        LocalDate date,

        @NotNull(message = "우선순위는 필수입니다.")
        @Min(1)
        @Max(4)
        Integer priority

) {
}