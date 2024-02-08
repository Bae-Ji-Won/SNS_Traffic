package com.example.sns_traffic_project.dto.response;

import com.example.sns_traffic_project.domain.constant.UserRole;
import com.example.sns_traffic_project.dto.UserDto;
import lombok.AllArgsConstructor;

public record UserJoinResponse(
        Long id,
        String userName,
        UserRole role
) {

    public static UserJoinResponse of(Long id, String userName, UserRole role){
        return new UserJoinResponse(id, userName, role);
    }


    public static UserJoinResponse from(UserDto userDto){
        return UserJoinResponse.of(
                userDto.id(),
                userDto.userName(),
                userDto.role()
        );
    }
}
