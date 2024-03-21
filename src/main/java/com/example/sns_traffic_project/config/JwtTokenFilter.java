package com.example.sns_traffic_project.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.filter.OncePerRequestFilter;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // get header
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        // header는 Bearer 로 시작하기에 Bearer로 시작하지 않을 경우
        if(header == null || header.startsWith("Bearer ")){
            log.error("Error occurs while getting header. header is null or invalid");
            filterChain.doFilter(request,response);
            return;
        }
        // 정상적인 header인 경우
        try{
            // header에 맨 처음값은 Bearer이고 그 다음값이 토큰값임
            final String token = header.split(" ")[1].trim();
            //TODO :
            String userName = 
        }
    }
}
