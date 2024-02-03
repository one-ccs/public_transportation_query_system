package com.example.publictransportationquerysystem.util;

import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.publictransportationquerysystem.entity.User;

public class JwtUtil {
    // 秘钥
    private static final String SECRET = "TvA1p5Bds3DODi$b2bfe2b68eff9b86dd354e00d3c3c7f533ce18fe8a6f33f7c3af52396b1bb";
    // 过期时间 单位秒
    private static final long EXPIRATION = 1800L;

    public static String createJWT(UserDetails userDetails, User user) {
        Date expireDate = new Date(System.currentTimeMillis() + EXPIRATION * 1000);

        return JWT.create()
            .withClaim("id", user.getId())
            .withClaim("username", userDetails.getUsername())
            .withClaim("password", userDetails.getPassword())
            .withClaim("authorities", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
            .withExpiresAt(expireDate)
            .withIssuedAt(new Date())
            .sign(Algorithm.HMAC256(SECRET));
    }
}
