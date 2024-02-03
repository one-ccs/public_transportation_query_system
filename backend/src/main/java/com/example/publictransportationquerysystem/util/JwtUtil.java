package com.example.publictransportationquerysystem.util;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtUtil {
    // 秘钥
    private static final String SECRET = "TvA1p5Bds3DODi$b2bfe2b68eff9b86dd354e00d3c3c7f533ce18fe8a6f33f7c3af52396b1bb";
    // 过期时间 单位秒
    private static final long EXPIRATION = 1800L;

    /**
     * 创建 JWT
     * @param userDetails
     * @param user
     * @return String
     */
    public static String createJWT(UserDetails userDetails, Integer id, String username) {
        Date expireDate = new Date(System.currentTimeMillis() + EXPIRATION * 1000);

        return JWT.create()
            .withJWTId(UUID.randomUUID().toString())
            .withClaim("id", id)
            .withClaim("username", username)
            .withClaim("authorities", userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority).toList()
            )
            .withExpiresAt(expireDate)
            .withIssuedAt(new Date())
            .sign(Algorithm.HMAC256(SECRET));
    }

    /**
     * 解析并验证 token 返回解码后的 JWT
     * @param headerToken
     * @return
     */
    public static DecodedJWT resolveJwt(String headerToken) {
        // 转换 token
        String token = convertToken(headerToken);
        if (token == null) {
            return null;
        }
        // 验证 token 是否有效且未过期
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        try {
            DecodedJWT verify = jwtVerifier.verify(token);
            Date expiresAt = verify.getExpiresAt();

            return new Date().after(expiresAt) ? null : verify;
        } catch (JWTVerificationException e) {
            return null;
        }
    }

    public static UserDetails toUserDetails(DecodedJWT jwt) {
        Map<String, Claim> claims = jwt.getClaims();
        return User
            .withUsername(claims.get("user").asString())
            .password("未设置")
            .authorities(claims.get("authorities").asArray(String.class))
            .build();
    }

    public static Integer toId(DecodedJWT jwt) {
        return jwt.getClaims().get("id").asInt();
    }

    private static String convertToken(String headerToken) {
        if (headerToken == null || !headerToken.startsWith("Bearer ")) {
            return null;
        }
        return  headerToken.substring(7);
    }
}
