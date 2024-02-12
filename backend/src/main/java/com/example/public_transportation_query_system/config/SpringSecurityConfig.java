package com.example.public_transportation_query_system.config;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.public_transportation_query_system.entity.po.User;
import com.example.public_transportation_query_system.entity.vo.Result;
import com.example.public_transportation_query_system.entity.vo.response.AuthorizeVO;
import com.example.public_transportation_query_system.filter.JwtAuthorizeFilter;
import com.example.public_transportation_query_system.service.impl.UserServiceImpl;
import com.example.public_transportation_query_system.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class SpringSecurityConfig {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    JwtAuthorizeFilter jwtAuthorizeFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return  http.authorizeHttpRequests(conf -> conf
            // 放行 SpringDoc
            .requestMatchers("/api/docs").permitAll()
            .requestMatchers("/api/api-docs/**").permitAll()
            .requestMatchers("/api/swagger-ui/**").permitAll()
            // 放行 SpringDoc 的 actuator
            .requestMatchers("/actuator/**").permitAll()
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
            .invalidateHttpSession(true)
            .clearAuthentication(true)
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
        User user = userServiceImpl.getUserByNameOrEmail(userDetails.getUsername());
        String token = jwtUtil.createJWT(userDetails, user.getId(), user.getUsername());
        AuthorizeVO authorizeVO = user.asViewObject(AuthorizeVO.class, v -> {
            v.setExpire(jwtUtil.expireTime());
            v.setToken(token);
            v.setRoles(userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .map(authority -> authority.replace("ROLE_", ""))
                .collect(Collectors.toList())
            );
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
