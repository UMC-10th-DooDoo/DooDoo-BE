package com.dooDoo.dooDoo.domain.user.service;

import com.dooDoo.dooDoo.domain.user.dto.AuthDto;
import com.dooDoo.dooDoo.domain.user.entity.User;
import com.dooDoo.dooDoo.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;

    @Transactional
    public User signup(AuthDto.SignupRequest req) {
        if (userRepository.existsByLoginId(req.getLoginId()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 사용 중인 아이디입니다.");
        if (userRepository.existsByNickname(req.getNickname()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 사용 중인 닉네임입니다.");

        User user = User.builder()
                .loginId(req.getLoginId())
                .password(req.getPassword())
                .nickname(req.getNickname())
                .createdAt(req.getCreatedAt())
                .build();
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User login(AuthDto.LoginRequest req) {
        User user = userRepository.findByLoginId(req.getLoginId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "아이디 또는 비밀번호가 일치하지 않습니다."));
        if (!user.getPassword().equals(req.getPassword()))
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "아이디 또는 비밀번호가 일치하지 않습니다.");
        return user;
    }
}
