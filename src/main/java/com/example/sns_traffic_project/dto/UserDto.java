package com.example.sns_traffic_project.dto;

import com.example.sns_traffic_project.domain.entity.User;
import com.example.sns_traffic_project.domain.constant.UserRole;
import java.time.LocalDateTime;

public record UserDto (
        Long id,
        String userName,
        String password,
        UserRole role,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime updatedAt,
        String modifiedBy
){

    public static UserDto of(Long userId,String userName, String password, UserRole role) {
        return new UserDto(userId, userName,password, role, null, userName, null, userName);
    }


    public static UserDto of(Long userId,String userName, String password,UserRole role,LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new UserDto(userId, userName,password, role, createdAt, userName, updatedAt, userName);
    }


    // Todo : entity -> dto
    public static UserDto from(User user){
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getRole(),
                user.getCreatedAt(),
                user.getUsername(),
                user.getUpdatedAt(),
                user.getUsername()
        );
    }

    // Todo : dto -> entity
    public User toEntity() {
        return User.of(
                userName,
                password
        );
    }
}