package com.moment.the.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Profile("release")
@RequiredArgsConstructor
@EnableWebSecurity
public class release_securityConfig extends WebSecurityConfigurerAdapter {
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
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .authorizeRequests()
                    .antMatchers(
                            "/*/uncomfortable/**"
                    ).permitAll()
                    .anyRequest().hasAnyRole("ADMIN")
        ;

    }
}