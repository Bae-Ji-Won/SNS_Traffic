package com.example.sns_traffic_project.service;

import com.example.sns_traffic_project.domain.User;
import com.example.sns_traffic_project.exception.SnsApplicationException;
import com.example.sns_traffic_project.fixture.UserFixture;
import com.example.sns_traffic_project.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;




    @Test
    @DisplayName("회원가입 성공")
    void CreatUser(){
        String userName = "userName";
        String password = "password";

        User userFixture = UserFixture.get(userName,password);

        when(userRepository.findByUserName(userName)).thenReturn(Optional.empty());
        when(userRepository.save(any())).thenReturn(Optional.of(userFixture));

        Assertions.assertDoesNotThrow(() -> userService.join(userName,password));
    }

    @Test
    @DisplayName("회원가입 실패 - 유저 중복")
    void CreateUserFail_userNameDuplication(){
        String userName = "userName";
        String password = "password";

        User userFixture = UserFixture.get(userName,password);

        when(userRepository.findByUserName(userName)).thenReturn(Optional.empty());
        when(userRepository.save(any())).thenReturn(Optional.of(userFixture));

        Assertions.assertThrows(SnsApplicationException.class, () -> userService.join(userName,password));
    }

    @Test
    @DisplayName("로그인 성공")
    void Login(){
        String userName = "userName";
        String password = "password";

        User userFixture = UserFixture.get(userName,password);

        when(userRepository.findByUserName(userName)).thenReturn(Optional.of(userFixture));
        Assertions.assertDoesNotThrow(() -> userService.login(userName,password));
    }

    @Test
    @DisplayName("로그인 실패 - 유저 중복")
    void LoginFail_userNameDuplication(){
        String userName = "userName";
        String password = "password";


        when(userRepository.findByUserName(userName)).thenReturn(Optional.empty());

        Assertions.assertThrows(SnsApplicationException.class, () -> userService.login(userName,password));
    }

    @Test
    @DisplayName("로그인 실패 - 비밀번호 틀릴 경우")
    void LoginFail_WrongPassword(){
        String userName = "userName";
        String password = "password";
        String worngPw = "wrongpw";

        User userFixture = UserFixture.get(userName,password);

        when(userRepository.findByUserName(userName)).thenReturn(Optional.of(userFixture));

        Assertions.assertThrows(SnsApplicationException.class, () -> userService.login(userName,password));
    }
}