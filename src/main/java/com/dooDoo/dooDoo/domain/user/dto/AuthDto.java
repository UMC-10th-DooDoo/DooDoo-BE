package com.dooDoo.dooDoo.domain.user.dto;

import jakarta.validation.constraints.*;
import lombok.*;

public class AuthDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SignupRequest {
        @Pattern(regexp = "^[A-Za-z0-9_]{4,30}$",
                message = "아이디는 4~30자의 영문·숫자만 가능합니다.")
        private String loginId;

        @Size(min = 8, max = 255, message = "비밀번호는 8자 이상이어야 합니다.")
        private String password;

        @Size(min = 2, max = 50, message = "닉네임은 2~50자로 입력해주세요.")
        private String nickname;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginRequest {
        @NotBlank private String loginId;
        @NotBlank private String password;
    }

    @Getter
    @Builder
    public static class UserResponse {
        private Long userId;
        private String loginId;
        private String nickname;
    }
}
