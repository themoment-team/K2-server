package com.moment.the.config.security;

import com.moment.the.domain.AdminDomain;
import com.moment.the.util.RedisUtil;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String jwtToken = httpServletRequest.getHeader("Authorization");

        String userEmail = null;
        String refreshJwt = null;
        String refreshUName = null;

        try{
            if(jwtToken != null){
                userEmail = jwtUtil.getUserEmail(jwtToken);
            }
            if(userEmail != null){
                UserDetails userDetails = myUserDetailsService.loadUserByUsername(userEmail);

                if(jwtUtil.validateToken(jwtToken, userDetails)){
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        }catch (ExpiredJwtException e){
            if(refreshJwt != null){
                refreshJwt = redisUtil.getData(refreshJwt);
            }
        }catch(Exception e){
            System.out.println("e = " + e);
        }

        //reFresh Token 발급하기
        try{
            if(refreshJwt != null){
                refreshUName = redisUtil.getData(refreshJwt);

                if(refreshUName.equals(jwtUtil.getUserEmail(refreshJwt))){
                    UserDetails userDetails = myUserDetailsService.loadUserByUsername(refreshUName);
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                    AdminDomain adminDomain = new AdminDomain();
                    adminDomain.Change_UserId(refreshUName);
                    String newToken = jwtUtil.generateToken(adminDomain);
                    redisUtil.setDataExpire(adminDomain.getUsername(), newToken, jwtUtil.REFRESH_TOKEN_VALIDATION_SECOND);
                }
            }
        }catch(ExpiredJwtException e){

        }

        filterChain.doFilter(httpServletRequest,httpServletResponse);

    }
}
