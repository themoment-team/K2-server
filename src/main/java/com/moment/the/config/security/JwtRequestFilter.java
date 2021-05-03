package com.moment.the.config.security;

import com.moment.the.advice.exception.AccessTokenExpiredException;
import com.moment.the.advice.exception.InvalidTokenException;
import com.moment.the.util.RedisUtil;
import io.jsonwebtoken.ExpiredJwtException;
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

        String userEmail = null;
        String refreshEmail = null;

        // accessToken 검사
        try{
            if(accessJwt != null && jwtUtil.getUserTokenType(accessJwt).equals(JwtUtil.ACCESS_TOKEN_NAME))
                userEmail = jwtUtil.getUserEmail(accessJwt);

            if(userEmail != null){
                System.out.println("jwt userEmail " + userEmail);
                UserDetails userDetails = myUserDetailsService.loadUserByUsername(userEmail);

                //토큰 발급후 유저 정보 확인
                if(jwtUtil.validateToken(accessJwt, userDetails)){
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        //accessToken 이 만료되었을때 refreshToken 을 사용하여 accessToken 재발급한다.
        }catch (ExpiredJwtException e){
            if(refreshJwt != null){
                refreshEmail = jwtUtil.getUserEmail(refreshJwt);
                if(refreshJwt.equals(redisUtil.getData(refreshEmail))){
                    String newJwt = jwtUtil.generateAccessToken(refreshEmail);
                    res.addHeader("JwtToken", newJwt);
                }
            }else{
                throw new AccessTokenExpiredException();
            }
        } catch(MalformedJwtException e){
            throw new InvalidTokenException();
        } catch(IllegalArgumentException e){ //헤더에 토큰이 없으면 NPE 발생 하여 추가하였다. 추가적인 의미는 없다.
        }
        catch(Exception e){ //알수없는 Exception
            throw new UnknownError();
        }

        filterChain.doFilter(req,res); //필터 체인을 따라 계속 다음에 존재하는 필터로 이동한다.

    }
}
