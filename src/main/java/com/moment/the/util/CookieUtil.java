package com.moment.the.util;

import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class CookieUtil {
    public Cookie createCookie(String cookieName, String value){
        // 쿠키 인스턴스 생성.
        Cookie token  = new Cookie(cookieName, value);
        // token 은 cookie 형태로 저장될 것.
        token.setHttpOnly(true);
        // 유효 범위 지정.
        token.setMaxAge((int)JwtUtil.TOKEN_VALIDATION_SECOND);
        token.setPath("/");
        return token;
    }
    public Cookie getCookie(HttpServletRequest request, String cookieName){
        final Cookie[] cookies = request.getCookies();
        if(cookies == null) return null;
        for(Cookie cookie : cookies){
            if(cookie.getName().equals(cookieName)){
                return cookie;
            }
        }
        return null;
    }
}
