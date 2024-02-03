package com.example.publictransportationquerysystem.config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.SecurityFilterChain;

import com.example.publictransportationquerysystem.common.Result;
import com.example.publictransportationquerysystem.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return  httpSecurity.authorizeHttpRequests(conf -> conf
            .requestMatchers("/api/user/login").permitAll()
            .anyRequest().authenticated()
        )
        .formLogin(conf -> conf
            .loginProcessingUrl("/api/user/login")
            .failureHandler(this::onAuthenticationFailure)
            .successHandler(this::onAuthenticationSuccess)
        )
        .logout(conf -> conf
            .logoutUrl("/api/auth/logout")
            .logoutSuccessHandler(this::onLogoutSuccess)
        )
        .exceptionHandling(conf -> conf
            .authenticationEntryPoint(this::onUnauthorized)
            .accessDeniedHandler(this::onAccessDeny)
        )
        .csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(conf -> conf
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )
        .build();
    }

    /**
     * 无权访问
     */
    public void onAccessDeny(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(Result.forbidden().toJsonString());
    }

    /**
     * 未登录
     */
    public void onUnauthorized(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(Result.unauthorized().toJsonString());
    }

    /**
     * 登录成功
     */
    public void onAuthenticationSuccess(
        HttpServletRequest request,
        HttpServletResponse response,
        Authentication authentication
    ) throws IOException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = JwtUtil.createJWT(userDetails, 1, "张三");

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(Result.success(token).toJsonString());
    }

    /**
     * 登录失败
     */
    public void onAuthenticationFailure(
        HttpServletRequest request,
        HttpServletResponse response,
        AuthenticationException exception
    ) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(Result.error(exception.getMessage()).toJsonString());
    }

    /**
     * 登出成功
     */
    public void onLogoutSuccess(
        HttpServletRequest request,
        HttpServletResponse response,
        Authentication authentication
    ) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write("登出成功");
    }
}
