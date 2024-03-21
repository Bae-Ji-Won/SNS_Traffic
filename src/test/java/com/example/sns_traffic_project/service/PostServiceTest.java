package com.example.sns_traffic_project.service;

import com.example.sns_traffic_project.domain.Post;
import com.example.sns_traffic_project.domain.User;
import com.example.sns_traffic_project.exception.ErrorCode;
import com.example.sns_traffic_project.exception.SnsApplicationException;
import com.example.sns_traffic_project.repository.PostRepository;
import com.example.sns_traffic_project.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @MockBean
    private PostRepository postRepository;

    @MockBean
    private UserRepository userRepository;

    @Test
    @DisplayName("포스트 작성 성공")
    void PostCreate(){

        String title = "title";
        String body = "body";
        String userName = "userName";

        when(userRepository.findByUserName(userName)).thenReturn(Optional.of(mock(User.class)));
        when(postRepository.save(any())).thenReturn(mock(Post.class));

        Assertions.assertDoesNotThrow(() -> postService.create(title, body, userName));
    }

    @Test
    @DisplayName("포스트 작성시 해당 유저가 존재하지 않는 경우")
    void PostCreate_Fail_NotUser(){
        String title = "title";
        String body = "body";
        String userName = "userName";

        when(userRepository.findByUserName(userName)).thenReturn(Optional.empty());
        when(postRepository.save(any())).thenReturn(mock(Post.class));

        SnsApplicationException e = Assertions.assertThrows(SnsApplicationException.class, () -> postService.create(title,body, userName));
        Assertions.assertEquals(ErrorCode.USER_NOT_FOUND, e.getErrorCode());
    }
}