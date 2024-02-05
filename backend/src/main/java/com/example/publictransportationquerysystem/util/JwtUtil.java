package com.example.publictransportationquerysystem.util;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.publictransportationquerysystem.common.GlobalVariable;

@Component
public class JwtUtil {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /**
     * 创建 JWT
     * @param userDetails
     * @param id
     * @param username
     * @return String
     */
    public String createJWT(UserDetails userDetails, Integer id, String username) {
        return JWT.create()
            .withJWTId(UUID.randomUUID().toString())
            .withClaim("id", id)
            .withClaim("username", username)
            .withClaim("authorities", userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority).toList()
            )
            .withExpiresAt(this.expireTime())
            .withIssuedAt(new Date())
            .sign(Algorithm.HMAC256(GlobalVariable.JWT_SECRET));
    }

    public Date expireTime() {
        return new Date(System.currentTimeMillis() + GlobalVariable.EXPIRATION * 1000);
    }

    /**
     * 让 token 失效
     * @param headerToken
     * @return
     */
    public Boolean invalidateJwt(String headerToken) {
        String token = this.convertToken(headerToken);
        if (token == null) return false;
        Algorithm algorithm = Algorithm.HMAC256(GlobalVariable.JWT_SECRET);
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        try {
            DecodedJWT jwt = jwtVerifier.verify(token);
            String id = jwt.getId();
            return this.deleteToken(id, jwt.getExpiresAt());
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    public Boolean deleteToken(String uuid, Date time) {
        if (this.isInvalidToken(uuid)) return false;
        Date now = new Date();
        Long expire = Math.max(time.getTime() - now.getTime(), 0);
        stringRedisTemplate.opsForValue().set(GlobalVariable.JWT_BLACK_LIST_PREFIX + uuid, "", expire, TimeUnit.MILLISECONDS);
        return true;
    }

    public Boolean isInvalidToken(String uuid) {
        return stringRedisTemplate.hasKey(GlobalVariable.JWT_BLACK_LIST_PREFIX + uuid);
    }

    /**
     * 解析并验证 token 返回解码后的 JWT
     * @param headerToken
     * @return
     */
    public DecodedJWT resolveJwt(String headerToken) {
        // 转换 token
        String token = this.convertToken(headerToken);
        if (token == null) return null;
        // 验证 token 是否有效且未过期
        Algorithm algorithm = Algorithm.HMAC256(GlobalVariable.JWT_SECRET);
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        try {
            DecodedJWT jwt = jwtVerifier.verify(token);
            if (this.isInvalidToken(jwt.getId())) {
                return null;
            }
            Date expiresAt = jwt.getExpiresAt();
            if (new Date().after(expiresAt)) {
                return null;
            }
            return jwt;
        } catch (JWTVerificationException e) {
            return null;
        }
    }

    public UserDetails toUserDetails(DecodedJWT jwt) {
        Map<String, Claim> claims = jwt.getClaims();
        return User
            .withUsername(claims.get("username").asString())
            .password("未设置")
            .authorities(claims.get("authorities").asArray(String.class))
            .build();
    }

    public Integer toId(DecodedJWT jwt) {
        return jwt.getClaims().get("id").asInt();
    }

    private String convertToken(String headerToken) {
        if (headerToken == null || !headerToken.startsWith("Bearer ")) {
            return null;
        }
        return  headerToken.substring(7);
    }
}
