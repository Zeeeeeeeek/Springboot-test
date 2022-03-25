package com.zhejianglab.spring3common.utils;

import cn.hutool.jwt.JWT;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chenze
 * @date 2022/3/25
 */
@Slf4j
public class JwtUtil {
    public static final long EXPIRATION_TIME = 60 * 60 * 1000L;// 令牌环有效期 60 min
    public static final byte[] SECRET = "spring3test".getBytes(StandardCharsets.UTF_8);//令牌环密钥
    public static final String TOKEN_PREFIX = "Bearer";//令牌环头标识
    public static final String HEADER_STRING = "Authorization";//配置令牌环在http heads中的键值
    public static final String ROLE = "ROLE";//自定义字段-角色字段
    public static final String ID = "userId";//自定义字段-角色字段


    /**
     * 默认过期时间生成token
     * @param userRole
     * @param userid
     * @return
     */
    public static String generateToken(String userRole, String userid) {
        return getTokenString(userRole, userid, EXPIRATION_TIME);
    }


    /**
     * 自定义过期时间生成token
     * @param userRole
     * @param userid
     * @param expirationTime
     * @return
     */
    public static String generateToken(String userRole, String userid, long expirationTime) {
        return getTokenString(userRole, userid, expirationTime);
    }

    private static String getTokenString(String userRole, String userid, long expirationTime) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(ROLE, userRole);
        map.put(ID, userid);
        String jwt = JWT.create()
                .addPayloads(map)
                .setIssuedAt(new Date())
                .setExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
                .setKey(SECRET)
                .sign();
        return TOKEN_PREFIX + " " + jwt;
    }

    /**
     * 令牌环校验
     * @param token
     * @return
     */
    public static Boolean validate(String token) {
        return JWT.of(token.replace(TOKEN_PREFIX + " ", "")).setKey(SECRET).verify();
    }

    /**
     * 获取body
     * @param token
     * @return
     */
    public static Map<String, Object> getClaims(String token) {
        return JWT.of(token.replace(TOKEN_PREFIX + " ", "")).setKey(SECRET).getPayloads();
    }

}
