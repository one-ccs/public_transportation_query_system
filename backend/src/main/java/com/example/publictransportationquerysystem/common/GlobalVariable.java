package com.example.publictransportationquerysystem.common;

/**
 * GlobalVariable 全局变量类
 */
public class GlobalVariable {
    // JWT 秘钥
    public static final String JWT_SECRET = "TvA1p5Bds3DODi$b2bfe2b68eff9b86dd354e00d3c3c7f533ce18fe8a6f33f7c3af52396b1bb";
    // JWT 过期时间 单位秒
    public static final Long EXPIRATION = 1800L;
    // JWT 黑名单前缀
    public static final String JWT_BLACK_LIST_PREFIX = "jwt:blacklist:";
    // 跨域过滤器优先级
    public static final int ORDER_CORS = -102;
}
