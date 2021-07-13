package com.moment.the.config.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    @Value("${spring.jwt.secret}")
    private String SECRET_KEY;
    
    public final static long ACCESS_TOKEN_EXPIRATION_TIME = 1000l * 3600 * 6;  // milli_sec * hour * 6 = 6hour
    public final static long REFRESH_TOKEN_EXPIRATION_TIME = 1000l * 3600 * 24 * 30 * 7;  // milli_sec X hour X day X month * 7 = 7month

    final static public String TYPE_OF_ACCESS_TOKEN = "accessToken";
    final static public String TYPE_OF_REFRESH_TOKEN = "refreshToken";

    final static public String TOKEN_CLAIM_NAME_FOR_USER_EMAIL = "userEmail"; // token의 user email 추출시 필요한 claims이름
    final static public String TOKEN_CLAIM_NAME_FOR_TOKEN_TYPE = "tokenType";

    private Key getSigningKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Claims extractAllClaims(String token) throws ExpiredJwtException {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey(SECRET_KEY))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUserEmail(String token){
        return extractAllClaims(token).get(TOKEN_CLAIM_NAME_FOR_USER_EMAIL, String.class);
    }

    public String getTokenType(String token){
        return extractAllClaims(token).get(TOKEN_CLAIM_NAME_FOR_TOKEN_TYPE, String.class);
    }

    public Boolean isTokenExpired(String token) {
        try{
            extractAllClaims(token).getExpiration();
            return false;
        }catch(ExpiredJwtException e){
            return true;
        }
    }

    public Boolean validateToken(final String token, final String tokenType) {
        return !isTokenExpired(token) && getTokenType(token).equals(tokenType);
    }

    public String doGenerateToken(String userEmail, String tokenType, long expireTime) {
        final Claims claims = Jwts.claims();
        claims.put("userEmail", userEmail);
        claims.put("tokenType", tokenType);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(getSigningKey(SECRET_KEY), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateAccessToken(String email) {
        return doGenerateToken(email, TYPE_OF_ACCESS_TOKEN, ACCESS_TOKEN_EXPIRATION_TIME);
    }

    public String generateRefreshToken(String email) {
        return doGenerateToken(email, TYPE_OF_REFRESH_TOKEN, REFRESH_TOKEN_EXPIRATION_TIME);
    }

}
