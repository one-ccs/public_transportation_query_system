package com.example.publictransportationquerysystem.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.publictransportationquerysystem.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 每次请求都会调用该过滤器
 */
public class JwtAuthorizeFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // 从请求头获取 token
        String authorization = request.getHeader("Authoriztion");
        // 解析 JWT
        DecodedJWT jwt = JwtUtil.resolveJwt(authorization);
        if (jwt != null) {
            UserDetails userDetails = JwtUtil.toUserDetails(jwt);
            UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            // 添加认证信息强制认证通过
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            request.setAttribute("id", JwtUtil.toId(jwt));
        }
    }
}
