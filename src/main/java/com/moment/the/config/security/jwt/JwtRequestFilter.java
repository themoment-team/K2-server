package com.moment.the.config.security.jwt;

import com.moment.the.config.security.auth.MyUserDetailsService;
import com.moment.the.exception.legacy.legacyException.InvalidTokenException;
import com.moment.the.exception.legacy.legacyException.UserNotFoundException;
import io.jsonwebtoken.JwtException;
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

/**
 * JwtToken을 검증하여 사용자가 회원인지 아닌지 판별하는 필터다.
 * 추후 인증방식이 세션으로 교체될 예정이다.
 *
 * @author  전지환, 정시원
 * @since 1.0.0
 * @version 1.3.1
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
    private final MyUserDetailsService myUserDetailsService;
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = req.getHeader("Authorization");
        // Access Token이 null이면 검증할 필요가 없다.
        if (accessToken != null) {
            String userEmail = accessTokenExtractEmail(accessToken);

            if(userEmail != null) registerUserinfoInSecurityContext(userEmail, req);
        }
        filterChain.doFilter(req, res);
    }

    /**
     * accessToken에서 userEmail claim 값을 추출한다.
     *
     * @param accessToken Access Token
     * @return userEmail - accessToken에서 정상적으로 email를 추출할때 user email을 반한한다.
     * @throws InvalidTokenException - accessToken이 null이 아니고 올바르지 않을때 발생한다.
     * @author 정시원
     */
    private String accessTokenExtractEmail(String accessToken) {
        try {
            return jwtUtil.getUserEmail(accessToken);
        } catch (JwtException | IllegalArgumentException e ) {
            throw new InvalidTokenException();
        }
    }

    /**
     * user email로 사용자의 유무를 판단해 SecurityContext에 유저를 등록한다.
     * 실질적인 로그인 로직이라 할 수 있다.
     *
     * @param userEmail - String
     * @param req       - HttpServletRequest
     * @throws UserNotFoundException - 해당 사용자가 없을 경우 throw 된다.
     * @author 정시원
     */
    private void registerUserinfoInSecurityContext(String userEmail, HttpServletRequest req) {
        try {
            UserDetails userDetails = myUserDetailsService.loadUserByUsername(userEmail);

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        } catch (NullPointerException e) {
            throw new UserNotFoundException();
        }
    }
}
