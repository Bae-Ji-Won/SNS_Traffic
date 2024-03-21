package com.example.sns_traffic_project.service;


import com.example.sns_traffic_project.domain.User;
import com.example.sns_traffic_project.dto.UserDto;
import com.example.sns_traffic_project.exception.ErrorCode;
import com.example.sns_traffic_project.exception.SnsApplicationException;
import com.example.sns_traffic_project.repository.UserRepository;
import com.example.sns_traffic_project.util.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    private final BCryptPasswordEncoder encoder;


    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token.expired-time-ms}")
    private Long expiredTimeMs;

    public UserDto join(String userName, String pw) {
        // 유저 이름을 통한 정보 찾기 만약 있다면 에러 발생
        userRepository.findByUserName(userName).ifPresent(it -> {
            throw new SnsApplicationException(ErrorCode.DUPLICATED_USER_NAME,String.format("%s is duplicated",userName));
        });

        User user = userRepository.save(User.of(userName, encoder.encode(pw)));
        return UserDto.from(user);
    }

    // Todo : implement
    @Transactional(readOnly = true)
    public String login(String userName, String password){
        // 회원가입 여부 체크
        User user = userRepository.findByUserName(userName).orElseThrow(() -> new SnsApplicationException(ErrorCode.USER_NOT_FOUND,String.format("%s not found",userName)));

        // 비밀번호 체크
        // encoder에서 지원해주는 mathces를 사용하여 암호화된 비밀번호와 입력받은 비밀번호를 비교함
        if(!encoder.matches(password, user.getPassword())){
            throw new SnsApplicationException(ErrorCode.INVALID_PASSWORD);
        }

        // 토큰 생성
        String token = JwtTokenUtils.generateToken(userName,secretKey,expiredTimeMs);

        return token;
    }
}
