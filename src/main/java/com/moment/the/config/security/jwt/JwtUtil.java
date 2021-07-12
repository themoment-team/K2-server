package com.moment.the.config.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
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

    final static public String ACCESS_TOKEN_NAME = "accessToken";
    final static public String REFRESH_TOKEN_NAME = "refreshToken";

    final static public String TOKEN_CLAIM_NAME_USER_EMAIL = "userEmail"; // token의 user email 추출시 필요한 claims이름
    final static public String TOKEN_CLAIM_NAME_TOKEN_TYPE = "tokenType";

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
        return extractAllClaims(token).get(TOKEN_CLAIM_NAME_USER_EMAIL, String.class);
    }

    public String getTokenType(String token){
        return extractAllClaims(token).get(TOKEN_CLAIM_NAME_TOKEN_TYPE, String.class);
    }

    public Boolean isTokenExpired(String token) {
        final Date expiration = extractAllClaims(token).getExpiration();
        return expiration.before(new Date());
    }

    public String generateAccessToken(String email) {
        return doGenerateToken(email, ACCESS_TOKEN_NAME, ACCESS_TOKEN_EXPIRATION_TIME);
    }

    public String generateRefreshToken(String email) {
        return doGenerateToken(email, REFRESH_TOKEN_NAME, REFRESH_TOKEN_EXPIRATION_TIME);
    }

    public String doGenerateToken(String userEmail, String tokenType, long expireTime) {
        final Claims claims = Jwts.claims();
        claims.put("userEmail", userEmail);
        claims.put("tokenType", tokenType);
        String jwt = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(getSigningKey(SECRET_KEY), SignatureAlgorithm.HS256)
                .compact();
        return jwt;
    }

    public Boolean validateGeneralToken(String token){
        try{
            extractAllClaims(token);
        }catch (ExpiredJwtException e){
            return false;
        }
        return !isTokenExpired(token);
    }

    public Boolean validateAccessToken(String token, UserDetails userDetails) {
        final String username = getUserEmail(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token) && getTokenType(token).equals(ACCESS_TOKEN_NAME);
    }
}
