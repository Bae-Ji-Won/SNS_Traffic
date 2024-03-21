package com.example.sns_traffic_project.controller;

import com.example.sns_traffic_project.dto.UserDto;
import com.example.sns_traffic_project.dto.request.UserJoinRequest;
import com.example.sns_traffic_project.dto.request.UserLoginRequest;
import com.example.sns_traffic_project.domain.User;
import com.example.sns_traffic_project.exception.ErrorCode;
import com.example.sns_traffic_project.exception.SnsApplicationException;
import com.example.sns_traffic_project.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;


    @Test
    @DisplayName("회원가입")
    public void CreateUser() throws Exception {
        String userName = "";
        String password = "";

        when(userService.join(userName,password)).thenReturn(mock(UserDto.class));

        mockMvc.perform(post("/api/v1/users/join")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsBytes(new UserJoinRequest(userName,password)))
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("회원가입시_이미_회원가입된_userName으로_회원가입을_하는경우_에러반환")
    public void CreateUserFail_userNameDuplication() throws Exception {
        String userName = "userName";
        String password = "password";

        when(userService.join(userName,password)).thenThrow(new SnsApplicationException(ErrorCode.DUPLICATED_USER_NAME));

        mockMvc.perform(post("/api/v1/users/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserJoinRequest(userName,password)))
                ).andDo(print())
                .andExpect(status().isConflict());
        // isConflict() 메소드는 주어진 상태가 충돌 상태인지를 확인하는 데 사용됩니다. 충돌 상태란 동시에 여러 사용자 또는 프로세스가 동일한 자원을 수정하려고 할 때 발생하는 상황을 의미
    }


    @Test
    @DisplayName("로그인")
    public void Login() throws Exception {
        String userName = "userName";
        String password = "password";

        when(userService.login(userName,password)).thenReturn("test_token");

        mockMvc.perform(post("/api/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserLoginRequest(userName,password)))
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("로그인 실패 - 회원가입이 안된 Username")
    public void LoginFail_NotFoundUserName() throws Exception {
        String userName = "userName";
        String password = "password";

        when(userService.login(userName,password)).thenThrow(new SnsApplicationException(ErrorCode.USER_NOT_FOUND));

        mockMvc.perform(post("/api/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserLoginRequest(userName,password)))
                ).andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("로그인 실패 - 비밀번호 틀림")
    public void LoginFail_WrongPassword() throws Exception {
        String userName = "userName";
        String password = "password";

        when(userService.login(userName,password)).thenThrow(new SnsApplicationException(ErrorCode.INVALID_PASSWORD));

        mockMvc.perform(post("/api/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserLoginRequest(userName,password)))
                ).andDo(print())
                .andExpect(status().isUnauthorized());
    }
}