package com.example.public_transportation_query_system.entity.vo.response;

import java.util.List;

import lombok.Data;

/**
 * 登录验证成功的用户信息
 */
@Data
public class AuthorizeVO {
    Integer id;
    String username;
    List<String> roles;
    String token;
    String avatar;
}
