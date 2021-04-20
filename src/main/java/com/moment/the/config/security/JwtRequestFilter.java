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
import java.rmi.server.ExportException;

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

        try{
            if(accessJwt != null)
                userEmail = jwtUtil.getUserEmail(accessJwt);

            if(userEmail != null){
                System.out.println("jwt userEmail " + userEmail);
                UserDetails userDetails = myUserDetailsService.loadUserByUsername(userEmail);

                if(jwtUtil.validateToken(accessJwt, userDetails)){
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        }catch (ExpiredJwtException e){
            System.out.println(refreshJwt);
            if(refreshJwt != null){
                refreshEmail = jwtUtil.getUserEmail(refreshJwt);
                if(refreshJwt.equals(redisUtil.getData(refreshEmail))){
                    String newJwt = jwtUtil.generateToken(refreshEmail);
                    res.addHeader("JwtToken", newJwt);
                    accessJwt = newJwt;
                }
            }
        }catch(Exception e){
            System.out.println("e = " + e);
        }finally {
            filterChain.doFilter(req,res);
        }

        //reFresh Token 발급하기
//        try{
//            if(refreshJwt != null){
//                refreshEmail = redisUtil.getData(refreshJwt);
//
//                if(refreshEmail.equals(jwtUtil.getUserEmail(refreshJwt))){
//                    UserDetails userDetails = myUserDetailsService.loadUserByUsername(refreshEmail);
//                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
//                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
//                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//
//                    AdminDomain adminDomain = new AdminDomain();
//                    adminDomain.Change_UserId(refreshEmail);
//                    String newToken = jwtUtil.generateToken(adminDomain);
//                    redisUtil.setDataExpire(adminDomain.getUsername(), newToken, jwtUtil.REFRESH_TOKEN_VALIDATION_SECOND);
//                }
//            }
//        }catch(ExpiredJwtException e){
//            System.out.println(e);
//        }


    }
}
