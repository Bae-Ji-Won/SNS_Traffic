package com.example.sns_traffic_project.config.filter;

import com.example.sns_traffic_project.domain.entity.User;
import com.example.sns_traffic_project.service.UserService;
import com.example.sns_traffic_project.util.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {


    private final String key;
    private final UserService userService;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // get header
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        // header는 Bearer 로 시작하기에 Bearer로 시작하지 않을 경우
        if(header == null || !header.startsWith("Bearer ")){
            log.error("Error occurs while getting header. header is null or invalid");
            // 다음 Filter로 request, response를 넘김 (인증이 필요없는 경우도 있으므로)
            filterChain.doFilter(request,response);
            return;
        }
        // 정상적인 header인 경우
        try{
            // header에 맨 처음값은 Bearer이고 그 다음값이 토큰값임
            final String token = header.split(" ")[1].trim();

            // check token is valid
            if(JwtTokenUtils.isExpired(token,key)){
                log.error("Key is expired");
                filterChain.doFilter(request,response);
                return;
            }

            // get username from token
            // 외부에서 받아온 토큰의 유저가 존재하는지 확인
            String userName = JwtTokenUtils.getuserName(token, key);
            User user =  userService.loadUserByUserName(userName);


            // check the userName is valid
            // UsernamePasswordAuthenticationToken : 사용자가 이미 인증되었을 때, 사용자의 인증 정보와 권한을 스프링 시큐리티 컨텍스트에 등록하는데 사용됨
            //                                       애플리케이션 내에서 사용자가 인증되었음을 인식하고, 사용자의 권한에 따라 접근 제어 가능
            /* UsernamePasswordAuthenticationToken(1,2,3)
                1. Entity 객체
                2. Credentials로, 이미 인증된 사용자의 정보를 바탕으로 토큰을 생성하는 상황에서는 비밀번호 정보 필요 X하며 null
                3. Authorities로, 사용자가 가진 권한을 나타냄 (이전에 권한 종류들을 만들었던것) - User Entity에 getAuthorities 메서드로 구분하여 사용함
             */
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    user,null, user.getAuthorities()
            );


            /*
            WebAuthenticationDetailsSource : 웹 요청으로부터 인증 세부 정보를 생성하는 데 사용
            buildDetails : 실제 웹 요청(HttpServletRequest 객체)로부터 인증 세부 정보를 생성
            authentication.setDetails : UsernamePasswordAuthenticationToken객체의 details 속성에 설정

            사용자가 로그인을 시도할 때, 사용자의 IP 주소나 세션 ID 같은 정보를 함께 추적하고 싶다면,
            WebAuthenticationDetails를 사용하여 이러한 정보를 Authentication 객체에 담을 수 있습니다.
             */
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // Controller에서 유저의 정보를 바로 받을 수 있도록 하기 위함
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }catch(RuntimeException e){
            log.error("Error occurs while validating. {}",e.toString());
            filterChain.doFilter(request, response);
        }
        filterChain.doFilter(request,response);
    }
}
