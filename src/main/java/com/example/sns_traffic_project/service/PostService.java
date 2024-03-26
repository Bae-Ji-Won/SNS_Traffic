package com.example.sns_traffic_project.service;

import com.example.sns_traffic_project.domain.entity.Post;
import com.example.sns_traffic_project.domain.entity.User;
import com.example.sns_traffic_project.exception.ErrorCode;
import com.example.sns_traffic_project.exception.SnsApplicationException;
import com.example.sns_traffic_project.repository.PostRepository;
import com.example.sns_traffic_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    @Transactional
    public void create(String title, String body, String userName){
        // user find
        User user = userRepository.findByUserName(userName).orElseThrow(() -> new SnsApplicationException(ErrorCode.USER_NOT_FOUND,String.format("%s not founded",userName)));

        // post save
        Post post = postRepository.save(Post.of(title,body,user));
        // return
    }

}
