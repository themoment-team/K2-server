package com.moment.the.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Profile("dev")
@RequiredArgsConstructor
@Configuration
public class development_securityConfig extends WebSecurityConfigurerAdapter {

    @Override // ignore check swagger resource
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(
                "/v2/api-docs"
                , "/swagger-resources/**"
                , "/swagger-ui.html"
                , "/webjars/**"
                , "/swagger/**"
                , "/h2-console/**"
                , "/configuration/ui");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable() // rest api 이므로 기본설정 사용안함. 기본설정은 비인증시 로그인폼 화면으로 리다이렉트
                .csrf().disable() //rest api이므로 csrf 보안이 필요없으므로 disable
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // jwt 으로 인증할것이므로 세션필요x
            .and()
                //개발모드 이므로 다 사용가능
                .authorizeRequests().anyRequest().permitAll()
            ;

    }
}
