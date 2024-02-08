package com.example.sns_traffic_project.domain.constant;

import lombok.Getter;

public enum UserRole {

    ADMIN("관리자",true),
    USER("일반유저",true),
    VIEWER("뷰어",false);

    @Getter
    private final String description;           // 설명
    @Getter
    private final Boolean update;               // 업데이트 가능 여부

    UserRole(String description, Boolean update) {
        this.description = description;
        this.update = update;
    }
}
