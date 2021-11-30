package com.example.demo.jwt;

import com.example.demo.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;

@Configuration
public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final ApplicationContext context;

    @Autowired
    public JwtSecurityConfig(ApplicationContext context) {
        this.context = context;
    }

    @Bean
    public JwtAuthenticationProvider jwtAuthenticationProvider(JwtProperties jwtProperties, MemberService memberService) {
        return new JwtAuthenticationProvider(jwtProperties, memberService);
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(JwtProperties jwtProperties) {
        return new JwtAuthenticationFilter(jwtProperties);
    }

    @Override
    public void configure(HttpSecurity http) {
        JwtAuthenticationFilter jwtAuthenticationFilter = context.getBean(JwtAuthenticationFilter.class);
        http.addFilterAfter(jwtAuthenticationFilter, SecurityContextPersistenceFilter.class);
    }

}
