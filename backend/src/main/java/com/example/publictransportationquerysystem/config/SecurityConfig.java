package com.example.publictransportationquerysystem.config;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.publictransportationquerysystem.entity.po.Account;
import com.example.publictransportationquerysystem.entity.vo.Result;
import com.example.publictransportationquerysystem.entity.vo.response.AuthorizeVO;
import com.example.publictransportationquerysystem.filter.JwtAuthorizeFilter;
import com.example.publictransportationquerysystem.service.impl.AccountServiceImpl;
import com.example.publictransportationquerysystem.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfig {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    AccountServiceImpl accountServiceImpl;

    @Autowired
    JwtAuthorizeFilter jwtAuthorizeFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return  httpSecurity.authorizeHttpRequests(conf -> conf
            // 放行 SpringDoc
            .requestMatchers("/api/docs").permitAll()
            .requestMatchers("/api/api-docs/**").permitAll()
            .requestMatchers("/api/swagger-ui/**").permitAll()
            // 其它所有接口均需认证
            .anyRequest().authenticated()
        )
        .formLogin(conf -> conf
            // 登录接口默认自动放行
            .loginProcessingUrl("/api/auth/login")
            .failureHandler(this::onAuthenticationFailure)
            .successHandler(this::onAuthenticationSuccess)
        )
        .logout(conf -> conf
            // 登出接口默认自动放行
            .logoutUrl("/api/auth/logout")
            .logoutSuccessHandler(this::onLogoutSuccess)
        )
        .exceptionHandling(conf -> conf
            .authenticationEntryPoint(this::onUnauthorized)
            .accessDeniedHandler(this::onAccessDeny)
        )
        .cors(conf -> conf
            .disable()
        )
        .csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(conf -> conf
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )
        .addFilterBefore(jwtAuthorizeFilter, UsernamePasswordAuthenticationFilter.class)
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
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Account account = accountServiceImpl.getUserByNameOrEmail(userDetails.getUsername());
        String token = jwtUtil.createJWT(userDetails, account.getId(), account.getUsername());
        AuthorizeVO authorizeVO = account.asViewObject(AuthorizeVO.class, v -> {
            v.setExpire(jwtUtil.expireTime());
            v.setToken(token);
        });

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(Result.success(authorizeVO).toJsonString());
    }

    /**
     * 登录失败
     */
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(Result.failure(400, exception.getMessage()).toJsonString());
    }

    /**
     * 登出成功
     */
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        String authorization = request.getHeader("Authorization");
        if (jwtUtil.invalidateJwt(authorization)) {
            writer.write(Result.success("登出成功").toJsonString());
        } else {
            writer.write(Result.failure(400, "登出失败").toJsonString());
        }
    }
}
