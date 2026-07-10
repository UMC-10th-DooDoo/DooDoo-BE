package com.dooDoo.dooDoo.domain.todo.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TodoPriority {

    FIRST(1),
    SECOND(2),
    THIRD(3),
    FOURTH(4);

    private final int value;
}
