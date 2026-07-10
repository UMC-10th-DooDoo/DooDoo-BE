package com.dooDoo.dooDoo.domain.todo.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TodoCategory {

    STUDY("공부"),
    EXERCISE("운동"),
    WORK("일"),
    HOBBY("취미");

    private final String displayName;
}