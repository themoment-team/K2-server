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
            log.debug("jwt userEmail = {}", userEmail);
            registerUserInfoToSecurityContext(userEmail, req);
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
     * @param accessToken Access Token
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

    /**
     * user email로 사용자의 유무를 판단해 SecurityContext에 유저를 등록한다.
     * @param userEmail - String
     * @param req - HttpServletRequest
     * @throws UserNotFoundException - 해당 사용자가 없을 경우 throw 된다.
     */
    private void registerUserInfoToSecurityContext(String userEmail, HttpServletRequest req){
        UserDetails userDetails;
        try{
            userDetails = myUserDetailsService.loadUserByUsername(userEmail);
        }catch (NullPointerException e){
            throw new UserNotFoundException();
        }

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }
}
