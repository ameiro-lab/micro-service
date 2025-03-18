package com.space.ap.util;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.security.Key;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

/*
 * Json Web Token ユーティリティクラス
 */
@Component
public class JwtUtil {
    private static final String SECRET_KEY = "your-very-secure-and-long-secret-key-32bytes!!"; // 32文字以上
    private static final long EXPIRATION_TIME = 1000 * 60 * 60; // 1時間

    private static final Key KEY = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

    /**
     * JWT を発行する。
     * @param username
     * @return
     */
    public static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username) // ユーザー名をセット
                .setIssuedAt(new Date()) // 発行日時
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 有効期限
                .signWith(KEY, SignatureAlgorithm.HS256) // 署名
                .compact();
    }

    /**
     * JWT を解析し、クレームを取得する。
     * @param token
     * @return
     */
    public static Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public boolean isTokenValid(String token, String username) {
        return (username.equals(extractUsername(token)) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }
}
