package com.dooDoo.dooDoo.domain.category.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryCreateRequest(
        @NotBlank(message = "분야 이름은 필수입니다.")
        @Size(max = 50, message = "분야 이름은 50자 이하로 입력해주세요.")
        String name,

        @NotBlank(message = "색상은 필수입니다.")
        String color
){}