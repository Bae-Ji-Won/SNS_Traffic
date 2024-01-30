//package com.example.sns_traffic_project.dto;
//
//import com.example.sns_traffic_project.entity.User;
//
//public record UserDto (
//        Long userId,
//        String userName,
//        String password
//){
//
//
//    public static UserDto of(Long id, String userName, String pw){
//        return new UserDto(id,userName,pw);
//    }
//
//    public User toEntity(){
//        return User.of(
//                userId,
//                userName,
//                password
//        );
//    }
//}
