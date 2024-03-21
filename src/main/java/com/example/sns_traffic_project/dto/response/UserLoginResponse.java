package com.example.sns_traffic_project.dto.response;

import com.example.sns_traffic_project.domain.constant.UserRole;
import com.example.sns_traffic_project.dto.UserDto;

public record UserLoginResponse(
        String token
) {

}
