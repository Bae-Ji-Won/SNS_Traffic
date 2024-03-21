package com.example.sns_traffic_project.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

public class JwtTokenUtils {

    public static String generateToken(String userName, String key, long expiredTimeMs){
        // claims에 username 저장
        Claims claims = Jwts.claims();
        claims.put("userName",userName);

        return Jwts.builder()
                // 위에서 만든 claims을 토큰에 넣고
                .setClaims(claims)
                // 현재 토큰 발행 시간 넣고
                .setIssuedAt(new Date(System.currentTimeMillis()))
                // 토큰 만료 시간 적고
                .setExpiration(new Date(System.currentTimeMillis() + expiredTimeMs))
                .signWith(getKey(key),SignatureAlgorithm.HS256)
                .compact();
    }

    public static Key getKey(String key){
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
