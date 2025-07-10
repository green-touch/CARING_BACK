package com.caring.apigateway_service.util;

import com.caring.apigateway_service.dto.MemberInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;

import javax.crypto.SecretKey;

@Slf4j
public class TokenUtil {

    public static MemberInfo parseJwt(String jwt, String secretKey) {
        try {
            byte[] keyBytes = Decoders.BASE64.decode(secretKey); // Base64 디코딩 추가
            SecretKey key = Keys.hmacShaKeyFor(keyBytes);        // Key 객체 생성

            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();

            if (claims.getSubject() != null) {
                return MemberInfo.builder()
                        .roles(claims.get("auth", String.class))
                        .memberCode(claims.getSubject())
                        .build();
            }
            return null;
        } catch (Exception e) {
            log.warn("JWT validation failed with secret: {}", secretKey, e);
            return null;
        }
    }

    public static String resolveToken(ServerHttpRequest request) {
        return request.getHeaders()
                .get(HttpHeaders.AUTHORIZATION)
                .get(0).replace("Bearer ", "");
    }
}
