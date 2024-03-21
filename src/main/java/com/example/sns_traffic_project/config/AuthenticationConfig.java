package com.example.sns_traffic_project.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class AuthenticationConfig{

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeRequests(auth -> auth
                        .antMatchers("/api/*/users/join","api/*/users/login").permitAll()       // 해당 api 모두 허용
                        .antMatchers("/api/**").authenticated()     // 위의 api를 제외한 나머지는 모두 인증 필요
                )
                // 세션 따로 관리 안함
                .sessionManagement(se -> se
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // 로그인할때 유저정보를 토큰에 받아오기 위한 설정
                .addFilterBefore(new JwtTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .build();
                // security에서 인증하다 에러 발생시 handling
                // TODO
//                .exceptionHandling()
//                .authenticationEntryPoint()

    }
}
