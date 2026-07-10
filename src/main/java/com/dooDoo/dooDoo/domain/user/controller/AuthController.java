package com.dooDoo.dooDoo.domain.user.controller;

import com.dooDoo.dooDoo.domain.user.dto.AuthDto;
import com.dooDoo.dooDoo.domain.user.entity.User;
import com.dooDoo.dooDoo.domain.user.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthDto.UserResponse signup(@Valid @RequestBody AuthDto.SignupRequest req) {
        return toDto(authService.signup(req));
    }

    @PostMapping("/login")
    public AuthDto.UserResponse login(@Valid @RequestBody AuthDto.LoginRequest req,
                                      HttpServletRequest request) {
        User user = authService.login(req);
        HttpSession session = request.getSession();
        session.setAttribute("userId", user.getId());
        return toDto(user);
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(HttpServletRequest request) {
        request.getSession().invalidate();
    }

    private AuthDto.UserResponse toDto(User u) {
        return AuthDto.UserResponse.builder()
                .userId(u.getId())
                .loginId(u.getLoginId())
                .nickname(u.getNickname())
                .build();
    }
}
