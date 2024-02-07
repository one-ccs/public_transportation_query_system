package com.example.public_transportation_query_system.entity.vo.response;

import java.util.Date;

import lombok.Data;

/**
 * 登录验证成功的用户信息
 */
@Data
public class AuthorizeVO {
    String username;
    String role;
    String token;
    Date expire;
}
