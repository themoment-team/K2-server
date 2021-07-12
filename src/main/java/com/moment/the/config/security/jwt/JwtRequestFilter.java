package com.moment.the.config.security.jwt;

import com.moment.the.config.security.auth.MyUserDetailsService;
import com.moment.the.exceptionAdvice.exception.InvalidTokenException;
import com.moment.the.exceptionAdvice.exception.UserNotFoundException;
import com.moment.the.util.RedisUtil;
import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
    private final MyUserDetailsService myUserDetailsService;
    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        String accessJwt = req.getHeader("Authorization");
        String refreshJwt = req.getHeader("RefreshToken");

        String userEmail = accessTokenExtractEmail(accessJwt);

        // accessToken 검사
        if (userEmail != null) {
            log.debug("jwt userEmail {}", userEmail);
            try {
                // 토큰에서 추출한 user email를 통해 유저정보를 찾아 Security Context에 등록한다. 유저정보가 없으면 NullPointerException이 발생한다.
                registerUserInfoToSecurityContext(userEmail, req);
            } catch (NullPointerException e) {
                throw new UserNotFoundException();
            }
        }

        //accessToken 이 만료되었을때 refreshToken 을 사용하여 accessToken 재발급한다.
//        }catch (ExpiredJwtException e){
//            if(refreshJwt != null){
//                refreshEmail = jwtUtil.getUserEmail(refreshJwt);
//                if(refreshJwt.equals(redisUtil.getData(refreshEmail))){
//                    String newJwt = jwtUtil.generateAccessToken(refreshEmail);
//                    res.addHeader("JwtToken", newJwt);
//                }
//            }else{
//                throw new AccessTokenExpiredException();
//            }

        filterChain.doFilter(req,res); //필터 체인을 따라 계속 다음에 존재하는 필터로 이동한다.
    }

    /**
     * accessToken에서 userEmail claim 값을 추출한다.
     * @param accessToken
     * @return userEmail - accessToken에서 정상적으로 email를 추출할때 user email을 반한한다.
     * @throws InvalidTokenException - accessToken이 null이 아니고 올바르지 않을때 발생한다.
     * @author 정시원
     */
    private String accessTokenExtractEmail(String accessToken){
        try{
            return jwtUtil.getUserEmail(accessToken);
        }catch(IllegalArgumentException e){
            return null;
        }catch(MalformedJwtException e) {
            throw new InvalidTokenException();
        }
    }

    private void registerUserInfoToSecurityContext(String userEmail, HttpServletRequest req) throws NullPointerException{
        UserDetails userDetails = myUserDetailsService.loadUserByUsername(userEmail);

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }
}
