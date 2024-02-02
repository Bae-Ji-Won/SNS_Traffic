package com.example.sns_traffic_project.dto.response;

import com.example.sns_traffic_project.domain.constant.UserRole;

public record UserJoinResponse(
        Long id,
        String userName,
        UserRole role
) {


}
