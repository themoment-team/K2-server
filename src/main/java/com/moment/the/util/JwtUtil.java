package com.moment.the.util;

import com.moment.the.domain.AdminDomain;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    public final static long TOKEN_VALIDATION_SECOND = 1000L * 10;
    public final static long REFRESH_TOKEN_VALIDATION_SECOND = 1000L * 60 * 24 * 2;

    final static public String ACCESS_TOKEN_NAME = "accessToken";
    final static public String REFRESH_TOKEN_NAME = "refreshToken";

    // jwt.secret의 값을 읽어와 SECRET_KEY로 사용하겠다는.
    @Value("${spring.jwt.secret}")
    private String SECRET_KEY;
    // 승인 키 받기.
    private Key getSigningKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    // 토큰이 유효한 토큰인지 검사한 후, 토큰에 담긴 Payload값을 가져온다.
    public Claims extractAllClaims(String token) throws ExpiredJwtException{
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey(SECRET_KEY))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    // Payload로 부터 userName을 가져온다.
    public String getUsername(String token){
        return extractAllClaims(token).get("username", String.class);
    }
    // 토큰이 만료됐는지 안됐는지 확인.
    public Boolean isTokenExpired(String token){
        final Date expiration = extractAllClaims(token).getExpiration();
        return expiration.before(new Date());
    }
    // Access Token 생성
    public String generateToken(AdminDomain adminDomain) {
        return doGenerateToken(adminDomain.getAdminName(), TOKEN_VALIDATION_SECOND);
    }
    // Refresh Token 생성
    public String generateRefreshToken(AdminDomain adminDomain) {
        return doGenerateToken(adminDomain.getAdminName(), REFRESH_TOKEN_VALIDATION_SECOND);
    }
    // 토큰을 생성, 페이로드에 담길 값은 username.
    public String doGenerateToken(String username, long expireTime) {
        Claims claims = Jwts.claims();
        claims.put("username", username);
        String jwt = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(getSigningKey(SECRET_KEY), SignatureAlgorithm.HS256)
                .compact();
        return jwt;
    }
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
