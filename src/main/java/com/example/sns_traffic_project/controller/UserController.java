package com.example.sns_traffic_project.controller;

import com.example.sns_traffic_project.dto.UserDto;
import com.example.sns_traffic_project.dto.request.UserJoinRequest;
import com.example.sns_traffic_project.dto.request.UserLoginRequest;
import com.example.sns_traffic_project.dto.response.Response;
import com.example.sns_traffic_project.dto.response.UserJoinResponse;
import com.example.sns_traffic_project.dto.response.UserLoginResponse;
import com.example.sns_traffic_project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/join")
    public Response<UserJoinResponse> join(@RequestBody UserJoinRequest request){
        UserDto dto = userService.join(request.userName(),request.password());
        UserJoinResponse response = UserJoinResponse.from(dto);
        return Response.success(response);
    }

    @PostMapping("/login")
    public Response<UserLoginResponse> login(@RequestBody UserLoginRequest request){
        String token = userService.login(request.userName(),request.password());
        return Response.success(new UserLoginResponse(token));
    }
}
