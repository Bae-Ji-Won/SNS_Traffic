package com.example.sns_traffic_project.service;


import com.example.sns_traffic_project.domain.User;
import com.example.sns_traffic_project.exception.SnsApplicationException;
import com.example.sns_traffic_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // Todo : implement
    public User join(String userName, String pw) {
        // 유저 이름을 통한 정보 찾기 만약 있다면 에러 발생
        userRepository.findByUserName(userName).ifPresent(it -> {
            throw new SnsApplicationException();
        });

        userRepository.save(new User());
        return new User();
    }

    // Todo : implement
    public String login(String userName, String password){
        // 회원가입 여부 체크
        User user = userRepository.findByUserName(userName).orElseThrow(() -> new SnsApplicationException());

        // 비밀번호 체크
        if(!user.getPassword().equals(password)){
            throw new SnsApplicationException();
        }

        // 토큰 생성
        
        return "";
    }
}
