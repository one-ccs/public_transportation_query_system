package com.example.public_transportation_query_system.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.public_transportation_query_system.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 每次请求都会调用该过滤器
 */
@Component
public class JwtAuthorizeFilter extends OncePerRequestFilter {

    @Autowired
    JwtUtil jwtUtil;

    @SuppressWarnings("null")
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        // 从请求头获取 token
        String authorization = request.getHeader("Authorization");
        // 解析 JWT
        DecodedJWT jwt = jwtUtil.resolveJwt(authorization);
        if (jwt != null) {
            UserDetails userDetails = jwtUtil.toUserDetails(jwt);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            // 添加认证信息强制认证通过
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            request.setAttribute("id", jwtUtil.toId(jwt));
        }
        chain.doFilter(request, response);
    }
}
